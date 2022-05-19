package com.gechev.discoverbulgaria.services.impl;

import com.cloudinary.Cloudinary;
import com.gechev.discoverbulgaria.constants.Constants;
import com.gechev.discoverbulgaria.data.models.*;
import com.gechev.discoverbulgaria.data.repositories.PoiRepository;
import com.gechev.discoverbulgaria.data.repositories.RegionRepository;
import com.gechev.discoverbulgaria.events.PoiEvent;
import com.gechev.discoverbulgaria.exceptions.PoiNotFoundException;
import com.gechev.discoverbulgaria.exceptions.RegionNotFoundException;
import com.gechev.discoverbulgaria.services.PoiService;
import com.gechev.discoverbulgaria.services.ValidationService;
import com.gechev.discoverbulgaria.services.models.PoiServiceModel;
import com.gechev.discoverbulgaria.web.models.PoiFormViewModel;
import com.gechev.discoverbulgaria.web.models.PoiViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PoiServiceImpl implements PoiService {

  private final ModelMapper mapper;
  private final ValidationService validationService;
  private final RegionRepository regionRepository;
  private final PoiRepository poiRepository;
  private final Cloudinary cloudinary;
  private final ApplicationEventPublisher applicationEventPublisher;

  public PoiServiceImpl(ModelMapper mapper, ValidationService validationService, RegionRepository regionRepository, PoiRepository poiRepository, Cloudinary cloudinary, ApplicationEventPublisher applicationEventPublisher) {
    this.mapper = mapper;
    this.validationService = validationService;
    this.regionRepository = regionRepository;
    this.poiRepository = poiRepository;
    this.cloudinary = cloudinary;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  @Transactional
  public List<PoiServiceModel> findAll() {
    return this.poiRepository.findAll()
      .stream()
      .sorted(Comparator.comparing(Poi::getTitle))
      .map(poi -> this.mapper.map(poi, PoiServiceModel.class))
      .collect(Collectors.toList());
  }

  @Override
  public void addOrEditPoi(PoiFormViewModel poiFormViewModel, boolean isEdit) {
    Poi poi;

    if (isEdit) {
      poi = this.poiRepository.findByTitle(poiFormViewModel.getOldTitle()).orElseThrow(() -> new PoiNotFoundException("Забележителността за редакция не бе намерена, моля опитайте отново."));

      poi.setTitle(poiFormViewModel.getTitle());
      poi.setAddress(poiFormViewModel.getAddress());
      poi.setDescription(poiFormViewModel.getDescription());
      poi.setType(poiFormViewModel.getType());
      poi.setImageUrl(poiFormViewModel.getImageUrl());
      poi.setReadMore(poiFormViewModel.getReadMore());

      Coordinates poiCoords = poi.getCoordinates();
      poiCoords.setLatitude(poiFormViewModel.getLatitude());
      poiCoords.setLongitude(poiFormViewModel.getLongitude());

    } else {
      poi = this.mapper.map(poiFormViewModel, Poi.class);

      Coordinates poiCoordinates = new Coordinates(poiFormViewModel.getLatitude(), poiFormViewModel.getLongitude());

      poi.setCoordinates(poiCoordinates);
    }

    Region poiRegion = this.regionRepository.findByRegionId(poiFormViewModel.getRegionId()).orElseThrow(() -> new RegionNotFoundException("Областта на тази забележителност не бе намерена."));

    poi.setRegion(poiRegion);

    this.poiRepository.save(poi);

    this.applicationEventPublisher.publishEvent(new PoiEvent(this));
  }

  @Override
  @Transactional
  public void seedPoi(PoiServiceModel[] poiServiceModels) {
    for (PoiServiceModel poiServiceModel : poiServiceModels) {
      //Validate poi model and print message if not valid
      if (!this.validationService.isValid(poiServiceModel)) {
        this.validationService.violations(poiServiceModel)
          .forEach(v -> System.out.printf("%s %s%n", v.getMessage(), v.getInvalidValue()));
        continue;
      }
      try {
        String regionId = poiServiceModel.getRegion().getRegionId();
        Region region = this.regionRepository.findByRegionId(regionId).orElseThrow(() -> new NoSuchElementException(String.format("could not find region with regionId: %s", regionId)));

        try {
          Poi poi = this.poiRepository.findByTitle(poiServiceModel.getTitle()).orElseThrow();
          System.out.printf("Poi %s already exists.%n", poi.getTitle());
        } catch (NoSuchElementException e) {
          Poi poi = this.mapper.map(poiServiceModel, Poi.class);
          poi.setRegion(region);

          HashMap<String, String> uploadMap = new HashMap<>();
          uploadMap.put("upload_preset", "poi_upload_server");

          File poiImg = new File(Constants.RESOURCES_DIR + poiServiceModel.getImageUrl());
          String cloudinaryUrl = this.cloudinary.uploader().upload(poiImg, uploadMap).get("secure_url").toString();
          poi.setImageUrl(cloudinaryUrl.substring(Constants.CLOUDINARY_BASE_URL.length()));

          this.poiRepository.saveAndFlush(poi);

          System.out.printf("Poi successfully added: %s%n", poiServiceModel.getTitle());
        }

      } catch (NoSuchElementException | IOException e) {
        System.out.printf("Poi not added, reason: %s%n", e.getMessage());
      }
    }
  }

  public List<PoiViewModel> getPoiViewModels() {
    return this.poiRepository.findAll()
      .stream()
      .sorted(Comparator.comparing(Poi::getTitle))
      .map(poi -> {
        PoiViewModel poiCard = mapper.map(poi, PoiViewModel.class);
        poiCard.setLatitude(poi.getCoordinates().getLatitude());
        poiCard.setLongitude(poi.getCoordinates().getLongitude());
        poiCard.setRegionId(poi.getRegion().getRegionId());
        poiCard.setImageUrl(Constants.CLOUDINARY_BASE_URL + poi.getImageUrl());
        return poiCard;
      })
      .collect(Collectors.toList());
  }

  @Transactional
  public List<PoiViewModel> getPoiByRegionId(String regionId) {
    Optional<Region> region = this.regionRepository.findByRegionId(regionId);
    return this.mapPoiToViewModelList(poiRepository.findAllByRegion(region.get()));
  }

  @Transactional
  public List<PoiViewModel> getPoiByRegionIdAndType(String regionId, Type type) {
    Optional<Region> region = this.regionRepository.findByRegionId(regionId);
    return this.mapPoiToViewModelList(poiRepository.findAllByRegionAndType(region.get(), type));
  }

  private List<PoiViewModel> mapPoiToViewModelList(Set<Poi> poi) {
    return poi.stream()
      .sorted(Comparator.comparing(Poi::getTitle))
      .map(p -> {
        PoiViewModel poiCard = this.mapper.map(p, PoiViewModel.class);
        poiCard.setRegionId(p.getRegion().getRegionId());
        poiCard.setLatitude(p.getCoordinates().getLatitude());
        poiCard.setLongitude(p.getCoordinates().getLongitude());
        poiCard.setImageUrl(Constants.CLOUDINARY_BASE_URL + p.getImageUrl());
        return poiCard;
      })
      .collect(Collectors.toList());
  }

  @Override
  public Long getRepositoryCount() {
    return this.poiRepository.count();
  }
}
