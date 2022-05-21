package com.gechev.discoverbulgaria.services.impl;

import com.cloudinary.Cloudinary;
import com.gechev.discoverbulgaria.constants.Constants;
import com.gechev.discoverbulgaria.data.models.Fact;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.models.Type;
import com.gechev.discoverbulgaria.data.repositories.FactRepository;
import com.gechev.discoverbulgaria.data.repositories.RegionRepository;
import com.gechev.discoverbulgaria.exceptions.FactNotFoundException;
import com.gechev.discoverbulgaria.exceptions.RegionNotFoundException;
import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.ValidationService;
import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.web.models.BaseViewModel;
import com.gechev.discoverbulgaria.web.models.DeleteModel;
import com.gechev.discoverbulgaria.web.models.FactViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FactServiceImpl implements FactService {

  private final ValidationService validationService;
  private final ModelMapper mapper;
  private final FactRepository factRepository;
  private final RegionRepository regionRepository;
  private final Cloudinary cloudinary;
  private final ApplicationEventPublisher applicationEventPublisher;

  public FactServiceImpl(ValidationService validationService, ModelMapper mapper, FactRepository factRepository, RegionRepository regionRepository, Cloudinary cloudinary, ApplicationEventPublisher applicationEventPublisher) {
    this.validationService = validationService;
    this.mapper = mapper;
    this.factRepository = factRepository;
    this.regionRepository = regionRepository;
    this.cloudinary = cloudinary;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  @Transactional
  public List<FactServiceModel> findAll() {
    return this.factRepository.findAll()
      .stream()
      .sorted(Comparator.comparing(Fact::getTitle))
      .map(r -> this.mapper.map(r, FactServiceModel.class))
      .collect(Collectors.toList());
  }

  @Transactional
  public List<FactViewModel> getFactsByRegionId(String regionId) {
    Optional<Region> region = this.regionRepository.findByRegionId(regionId);
    return this.mapFactsToViewModelList(factRepository.findAllByRegion(region.get()));
  }

  @Transactional
  public List<FactViewModel> getFactsByRegionIdAndType(String regionId, Type type) {
    Optional<Region> region = this.regionRepository.findByRegionId(regionId);
    return this.mapFactsToViewModelList(factRepository.findAllByRegionAndType(region.get(), type));
  }

  private List<FactViewModel> mapFactsToViewModelList(Set<Fact> facts) {
    return facts.stream()
      .sorted(Comparator.comparing(Fact::getTitle))
      .map(f -> {
        FactViewModel factCard = this.mapper.map(f, FactViewModel.class);
        factCard.setRegionId(f.getRegion().getRegionId());
        factCard.setImageUrl(Constants.CLOUDINARY_BASE_URL + f.getImageUrl());
        return factCard;
      })
      .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public DeleteModel deleteFact(BaseViewModel factDeleteModel) {
    this.factRepository.deleteById(factDeleteModel.getId());
    DeleteModel deletedFact = new DeleteModel(true);
    deletedFact.setId(factDeleteModel.getId());
    return deletedFact;
  }

  @Override
  @Transactional
  public FactViewModel addOrEditFact(FactViewModel factViewModel, boolean isEdit) {
    Fact fact;

    if (isEdit) {
      fact = this.factRepository.findById(factViewModel.getId()).orElseThrow(() -> new FactNotFoundException("Фактът за редакция не бе намерен, моля опитайте отново."));

      fact.setTitle(factViewModel.getTitle());
      fact.setDescription(factViewModel.getDescription());
      fact.setType(factViewModel.getType());
      fact.setImageUrl(factViewModel.getImageUrl());
      fact.setReadMore(factViewModel.getReadMore());
    } else {
      fact = this.mapper.map(factViewModel, Fact.class);
    }

    Region region = this.regionRepository.findByRegionId(factViewModel.getRegionId()).orElseThrow(() -> new RegionNotFoundException("Областта на този факт не бе намерена."));
    fact.setRegion(region);

    this.factRepository.save(fact);
    return factViewModel;
  }

  @Override
  public List<FactViewModel> getFactViewModels() {
    return this.factRepository.findAll()
      .stream()
      .sorted(Comparator.comparing(Fact::getTitle))
      .map(fact -> {
        FactViewModel factCard = this.mapper.map(fact, FactViewModel.class);
        factCard.setRegionId(fact.getRegion().getRegionId());
        factCard.setImageUrl(Constants.CLOUDINARY_BASE_URL + fact.getImageUrl());
        return factCard;
      })
      .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void seedFacts(FactServiceModel[] factServiceModels) {
    for (FactServiceModel factServiceModel : factServiceModels) {

      //Validate fact model and print message if not valid
      if (!this.validationService.isValid(factServiceModel)) {
        this.validationService.violations(factServiceModel)
          .forEach(v -> System.out.printf("%s %s%n", v.getMessage(), v.getInvalidValue()));
        continue;
      }
      try {
        String regionId = factServiceModel.getRegion().getRegionId();
        Region region = this.regionRepository.findByRegionId(regionId).orElseThrow(() -> new NoSuchElementException(String.format("could not find region with regionId: %s", regionId)));

        try {
          Fact fact = this.factRepository.findByTitle(factServiceModel.getTitle()).orElseThrow();
          System.out.printf("Fact %s already exists.%n", fact.getTitle());
        } catch (NoSuchElementException e) {
          Fact fact = this.mapper.map(factServiceModel, Fact.class);
          fact.setRegion(region);

          HashMap<String, String> uploadMap = new HashMap<>();
          uploadMap.put("upload_preset", "facts_upload_server");

          File factImg = new File(Constants.RESOURCES_DIR + factServiceModel.getImageUrl());
          String cloudinaryUrl = this.cloudinary.uploader().upload(factImg, uploadMap).get("secure_url").toString();
          fact.setImageUrl(cloudinaryUrl.substring(Constants.CLOUDINARY_BASE_URL.length()));

          this.factRepository.saveAndFlush(fact);

          System.out.printf("Fact successfully added: %s%n", factServiceModel.getImageUrl());
        }

      } catch (NoSuchElementException | IOException e) {
        System.out.printf("Fact not added, reason: %s%n", e.getMessage());
      }
    }
  }

  @Override
  public Long getRepositoryCount() {
    return this.factRepository.count();
  }
}
