package com.gechev.discoverbulgaria.services.impl;

import com.cloudinary.Cloudinary;
import com.gechev.discoverbulgaria.constants.Constants;
import com.gechev.discoverbulgaria.data.models.Fact;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.models.Type;
import com.gechev.discoverbulgaria.data.repositories.FactRepository;
import com.gechev.discoverbulgaria.data.repositories.RegionRepository;
import com.gechev.discoverbulgaria.events.FactEvent;
import com.gechev.discoverbulgaria.exceptions.FactNotFoundException;
import com.gechev.discoverbulgaria.exceptions.RegionNotFoundException;
import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.ValidationService;
import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.web.models.CardViewModel;
import com.gechev.discoverbulgaria.web.models.FactFormViewModel;
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
  public List<CardViewModel> getFactsByRegionId(String regionId) {
    Optional<Region> region = this.regionRepository.findByRegionId(regionId);
    return this.mapFactsToViewModelList(factRepository.findAllByRegion(region.get()));
  }

  @Transactional
  public List<CardViewModel> getFactsByRegionIdAndType(String regionId, Type type) {
    Optional<Region> region = this.regionRepository.findByRegionId(regionId);
    return this.mapFactsToViewModelList(factRepository.findAllByRegionAndType(region.get(), type));
  }

  private List<CardViewModel> mapFactsToViewModelList(Set<Fact> facts) {
    return facts.stream()
      .sorted(Comparator.comparing(Fact::getTitle))
      .map(f -> {
        CardViewModel factCard = this.mapper.map(f, CardViewModel.class);
        factCard.setRegionId(f.getRegion().getRegionId());
        factCard.setImageUrl(Constants.CLOUDINARY_BASE_URL + f.getImageUrl());
        return factCard;
      })
      .collect(Collectors.toList());
  }

  @Override
  public void addOrEditFact(FactFormViewModel factFormViewModel, boolean isEdit) {
    Fact fact;

    if (isEdit) {
      fact = this.factRepository.findByTitle(factFormViewModel.getOldTitle()).orElseThrow(() -> new FactNotFoundException("Фактът за редакция не бе намерен, моля опитайте отново."));

      fact.setTitle(factFormViewModel.getTitle());
      fact.setDescription(factFormViewModel.getDescription());
      fact.setType(factFormViewModel.getType());
      fact.setImageUrl(factFormViewModel.getImageUrl());
      fact.setReadMore(factFormViewModel.getReadMore());
    } else {
      fact = this.mapper.map(factFormViewModel, Fact.class);
    }

    Region region = this.regionRepository.findByRegionId(factFormViewModel.getRegionId()).orElseThrow(() -> new RegionNotFoundException("Областта на този факт не бе намерена."));
    fact.setRegion(region);

    this.factRepository.save(fact);

    this.applicationEventPublisher.publishEvent(new FactEvent(this));
  }

  @Override
  public List<FactFormViewModel> getFactViewModels() {
    return this.factRepository.findAll()
      .stream()
      .sorted(Comparator.comparing(Fact::getTitle))
      .map(fact -> {
        FactFormViewModel factFormViewModel = this.mapper.map(fact, FactFormViewModel.class);
        factFormViewModel.setRegionId(fact.getRegion().getRegionId());
        return factFormViewModel;
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
          .forEach(v -> System.out.println(String.format("%s %s", v.getMessage(), v.getInvalidValue())));
        continue;
      }
      try {
        String regionId = factServiceModel.getRegion().getRegionId();
        Region region = this.regionRepository.findByRegionId(regionId).orElseThrow(() -> new NoSuchElementException(String.format("could not find region with regionId: %s", regionId)));

        try {
          Fact fact = this.factRepository.findByTitle(factServiceModel.getTitle()).orElseThrow();
          System.out.println(String.format("Fact %s already exists.", fact.getTitle()));
        } catch (NoSuchElementException e) {
          Fact fact = this.mapper.map(factServiceModel, Fact.class);
          fact.setRegion(region);

          HashMap<String, String> uploadMap = new HashMap<>();
          uploadMap.put("upload_preset", "facts_upload_server");

          File factImg = new File(Constants.RESOURCES_DIR + factServiceModel.getImageUrl());
          String cloudinaryUrl = this.cloudinary.uploader().upload(factImg, uploadMap).get("secure_url").toString();
          fact.setImageUrl(cloudinaryUrl.substring(Constants.CLOUDINARY_BASE_URL.length()));

          this.factRepository.saveAndFlush(fact);

          System.out.println(String.format("Fact successfully added: %s", factServiceModel.getImageUrl()));
        }

      } catch (NoSuchElementException | IOException e) {
        System.out.println(String.format("Fact not added, reason: %s", e.getMessage()));
      }
    }
  }

  @Override
  public Long getRepositoryCount() {
    return this.factRepository.count();
  }
}
