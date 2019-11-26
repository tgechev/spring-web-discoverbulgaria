package com.gechev.discoverbulgaria.services.impl;

import com.cloudinary.Cloudinary;
import com.gechev.discoverbulgaria.constants.Constants;
import com.gechev.discoverbulgaria.data.models.Coordinates;
import com.gechev.discoverbulgaria.data.models.Poi;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.repositories.PoiRepository;
import com.gechev.discoverbulgaria.data.repositories.RegionRepository;
import com.gechev.discoverbulgaria.events.PoiEvent;
import com.gechev.discoverbulgaria.services.PoiService;
import com.gechev.discoverbulgaria.services.models.PoiServiceModel;
import com.gechev.discoverbulgaria.util.ValidatorUtil;
import com.gechev.discoverbulgaria.web.models.PoiFormViewModel;
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
    private final ValidatorUtil validatorUtil;
    private final RegionRepository regionRepository;
    private final PoiRepository poiRepository;
    private final Cloudinary cloudinary;
    private final ApplicationEventPublisher applicationEventPublisher;

    public PoiServiceImpl(ModelMapper mapper, ValidatorUtil validatorUtil, RegionRepository regionRepository, PoiRepository poiRepository, Cloudinary cloudinary, ApplicationEventPublisher applicationEventPublisher) {
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
        this.regionRepository = regionRepository;
        this.poiRepository = poiRepository;
        this.cloudinary = cloudinary;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    @Transactional
    public Set<PoiServiceModel> findAll() {
        return poiRepository.findAll()
                .stream()
                .map(poi -> mapper.map(poi, PoiServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public PoiServiceModel findByName(String name) {
        return null;
    }

    @Override
    public void addOrEditPoi(PoiFormViewModel poiFormViewModel, boolean isEdit) {
        Poi poi;

        if(isEdit){
            poi = poiRepository.findByTitle(poiFormViewModel.getOldTitle()).orElseThrow();

            poi.setTitle(poiFormViewModel.getTitle());
            poi.setAddress(poiFormViewModel.getAddress());
            poi.setDescription(poiFormViewModel.getDescription());
            poi.setType(poiFormViewModel.getType());
            poi.setImageUrl(poiFormViewModel.getImageUrl());
            poi.setReadMore(poiFormViewModel.getReadMore());

        }
        else{
            poi = mapper.map(poiFormViewModel, Poi.class);
        }

        Region poiRegion = regionRepository.findByRegionId(poiFormViewModel.getRegionId()).orElseThrow();

        Coordinates poiCoordinates = new Coordinates(poiFormViewModel.getLatitude(), poiFormViewModel.getLongitude());

        poi.setCoordinates(poiCoordinates);
        poi.setRegion(poiRegion);

        poiRepository.save(poi);

        applicationEventPublisher.publishEvent(new PoiEvent(this));
    }

    @Override
    @Transactional
    public void seedPoi(PoiServiceModel[] poiServiceModels) {
        for (PoiServiceModel poiServiceModel : poiServiceModels) {
            //Validate poi model and print message if not valid
            if(!validatorUtil.isValid(poiServiceModel)){
                this.validatorUtil.violations(poiServiceModel)
                        .forEach(v-> System.out.println(String.format("%s %s", v.getMessage(), v.getInvalidValue())));
                continue;
            }
            try {
                String regionId = poiServiceModel.getRegion().getRegionId();
                Region region = this.regionRepository.findByRegionId(regionId).orElseThrow(() -> new NoSuchElementException(String.format("could not find region with regionId: %s", regionId)));

                try{
                    Poi poi = poiRepository.findByTitle(poiServiceModel.getTitle()).orElseThrow();
                    System.out.println(String.format("Poi %s already exists.", poi.getTitle()));
                }

                catch(NoSuchElementException e) {
                    Poi poi = this.mapper.map(poiServiceModel, Poi.class);
                    poi.setRegion(region);

                    HashMap<String, String> uploadMap = new HashMap<>();
                    uploadMap.put("upload_preset", "poi_upload_server");

                    File poiImg = new File(Constants.RESOURCES_DIR + poiServiceModel.getImageUrl());
                    String cloudinaryUrl = cloudinary.uploader().upload(poiImg, uploadMap).get("secure_url").toString();
                    poi.setImageUrl(cloudinaryUrl.substring(Constants.CLOUDINARY_BASE_URL.length()));

                    this.poiRepository.saveAndFlush(poi);

                    System.out.println(String.format("Poi successfully added: %s", poiServiceModel.getTitle()));
                }

            }
            catch(NoSuchElementException | IOException e){
                System.out.println(String.format("Poi not added, reason: %s", e.getMessage()));
            }
        }
    }

    public List<PoiFormViewModel> getPoiViewModels(){
        return this.poiRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Poi::getTitle))
                .map(poi -> {
                    PoiFormViewModel poiFormViewModel = mapper.map(poi, PoiFormViewModel.class);
                    poiFormViewModel.setLatitude(poi.getCoordinates().getLatitude());
                    poiFormViewModel.setLongitude(poi.getCoordinates().getLongitude());
                    poiFormViewModel.setRegionId(poi.getRegion().getRegionId());
                    return poiFormViewModel;
                })
                .collect(Collectors.toList());
    }
}
