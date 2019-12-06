package com.gechev.discoverbulgaria.services.impl;

import com.cloudinary.Cloudinary;
import com.gechev.discoverbulgaria.constants.Constants;
import com.gechev.discoverbulgaria.data.models.Coordinates;
import com.gechev.discoverbulgaria.data.models.Poi;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.repositories.PoiRepository;
import com.gechev.discoverbulgaria.data.repositories.RegionRepository;
import com.gechev.discoverbulgaria.events.PoiEvent;
import com.gechev.discoverbulgaria.exceptions.PoiNotFoundException;
import com.gechev.discoverbulgaria.services.PoiService;
import com.gechev.discoverbulgaria.services.ValidationService;
import com.gechev.discoverbulgaria.services.models.PoiServiceModel;
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
    public Set<PoiServiceModel> findAll() {
        return this.poiRepository.findAll()
                .stream()
                .map(poi -> this.mapper.map(poi, PoiServiceModel.class))
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
            poi = this.poiRepository.findByTitle(poiFormViewModel.getOldTitle()).orElseThrow(()-> new PoiNotFoundException("Забележителността за редакция не бе намерена, моля опитайте отново."));

            poi.setTitle(poiFormViewModel.getTitle());
            poi.setAddress(poiFormViewModel.getAddress());
            poi.setDescription(poiFormViewModel.getDescription());
            poi.setType(poiFormViewModel.getType());
            poi.setImageUrl(poiFormViewModel.getImageUrl());
            poi.setReadMore(poiFormViewModel.getReadMore());

            Coordinates poiCoords = poi.getCoordinates();
            poiCoords.setLatitude(poiFormViewModel.getLatitude());
            poiCoords.setLongitude(poiFormViewModel.getLongitude());

        }
        else{
            poi = this.mapper.map(poiFormViewModel, Poi.class);

            Coordinates poiCoordinates = new Coordinates(poiFormViewModel.getLatitude(), poiFormViewModel.getLongitude());

            poi.setCoordinates(poiCoordinates);
        }

        Region poiRegion = this.regionRepository.findByRegionId(poiFormViewModel.getRegionId()).orElseThrow();

        poi.setRegion(poiRegion);

        this.poiRepository.save(poi);

        this.applicationEventPublisher.publishEvent(new PoiEvent(this));
    }

    @Override
    @Transactional
    public void seedPoi(PoiServiceModel[] poiServiceModels) {
        for (PoiServiceModel poiServiceModel : poiServiceModels) {
            //Validate poi model and print message if not valid
            if(!this.validationService.isValid(poiServiceModel)){
                this.validationService.violations(poiServiceModel)
                        .forEach(v-> System.out.println(String.format("%s %s", v.getMessage(), v.getInvalidValue())));
                continue;
            }
            try {
                String regionId = poiServiceModel.getRegion().getRegionId();
                Region region = this.regionRepository.findByRegionId(regionId).orElseThrow(() -> new NoSuchElementException(String.format("could not find region with regionId: %s", regionId)));

                try{
                    Poi poi = this.poiRepository.findByTitle(poiServiceModel.getTitle()).orElseThrow();
                    System.out.println(String.format("Poi %s already exists.", poi.getTitle()));
                }

                catch(NoSuchElementException e) {
                    Poi poi = this.mapper.map(poiServiceModel, Poi.class);
                    poi.setRegion(region);

                    HashMap<String, String> uploadMap = new HashMap<>();
                    uploadMap.put("upload_preset", "poi_upload_server");

                    File poiImg = new File(Constants.RESOURCES_DIR + poiServiceModel.getImageUrl());
                    String cloudinaryUrl = this.cloudinary.uploader().upload(poiImg, uploadMap).get("secure_url").toString();
                    poi.setImageUrl(cloudinaryUrl.substring(Constants.CLOUDINARY_BASE_URL.length()));

                    this.poiRepository.saveAndFlush(poi);

                    System.out.println(String.format("Poi successfully added: %s", poiServiceModel.getTitle()));
                }

            }
            catch(NoSuchElementException | IOException e){
                System.out.println(String.format("Poi not added, reason: %s", e.getMessage()));
            }
        }

        this.applicationEventPublisher.publishEvent(new PoiEvent(this));
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

    @Override
    public Long getRepositoryCount(){
        return this.poiRepository.count();
    }
}
