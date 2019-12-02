package com.gechev.discoverbulgaria.services.impl;

import com.cloudinary.Cloudinary;
import com.gechev.discoverbulgaria.constants.Constants;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.repositories.RegionRepository;
import com.gechev.discoverbulgaria.exceptions.RegionNotFoundException;
import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.services.ValidationService;
import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import com.gechev.discoverbulgaria.web.models.EditRegionModel;
import com.gechev.discoverbulgaria.web.models.RegionViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements RegionService {

    private  final RegionRepository regionRepository;
    private final ModelMapper mapper;
    private final ValidationService validationService;
    private final Cloudinary cloudinary;

    public RegionServiceImpl(RegionRepository regionRepository, ModelMapper mapper, ValidationService validationService, Cloudinary cloudinary) {
        this.regionRepository = regionRepository;
        this.mapper = mapper;
        this.validationService = validationService;
        this.cloudinary = cloudinary;
    }

    @Override
    @Transactional
    public List<RegionServiceModel> findAll() {
        return this.regionRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Region::getName))
                .map(r -> this.mapper.map(r, RegionServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public RegionServiceModel findByName(String name) {
        return this.mapper.map(this.regionRepository.findByName(name).orElseThrow(), RegionServiceModel.class);
    }

    @Override
    public RegionServiceModel findByRegionId(String regionId){
        return this.mapper.map(this.regionRepository.findByRegionId(regionId), RegionServiceModel.class);
    }

    @Override
    public void seedRegions(RegionServiceModel[] regionServiceModels) throws IOException {
        for (RegionServiceModel regionServiceModel : regionServiceModels) {
            //Validate region model and print message if not valid
            if(!this.validationService.isValid(regionServiceModel)){
                this.validationService.violations(regionServiceModel)
                        .forEach(v-> System.out.println(String.format("%s %s", v.getMessage(), v.getInvalidValue())));
                continue;
            }

            //If region already exists inform the user
            try{
                Region region = this.regionRepository.findByRegionId(regionServiceModel.getRegionId()).orElseThrow();
                System.out.println(String.format("Region %s already exists.", region.getName()));
            }

            //If region does not exist, create it.
            catch (NoSuchElementException e){
                Region region = mapper.map(regionServiceModel, Region.class);
                File regionImg = new File(Constants.RESOURCES_DIR + regionServiceModel.getImageUrl());
                String cloudinaryUrl = this.cloudinary.uploader().upload(regionImg, new HashMap()).get("secure_url").toString();
                region.setImageUrl(cloudinaryUrl.substring(Constants.CLOUDINARY_BASE_URL.length()));
                this.regionRepository.saveAndFlush(region);
                System.out.println(String.format("Region %s successfully created.", region.getName()));
            }
        }
    }

    @Override
    public boolean editRegion(EditRegionModel editRegionModel) {

        RegionServiceModel serviceModel = this.mapper.map(editRegionModel, RegionServiceModel.class);

        if(!this.validationService.isValid(serviceModel)){
            //throw error
            this.validationService.violations(serviceModel).forEach(v-> System.out.println(String.format("%s %s", v.getMessage(), v.getInvalidValue())));
            return false;
        }

        Region regionToUpdate = this.regionRepository.findByRegionId(editRegionModel.getTheId()).orElseThrow(()-> new RegionNotFoundException("Не е намерена област със съвпадащ ID номер, моля опитайте отново."));

        regionToUpdate.setName(serviceModel.getName());
        regionToUpdate.setRegionId(serviceModel.getRegionId());
        regionToUpdate.setArea(serviceModel.getArea());
        regionToUpdate.setPopulation(serviceModel.getPopulation());

        this.regionRepository.save(regionToUpdate);

        return true;
    }

    @Override
    @Transactional
    public List<RegionViewModel> getRegionViewModels(){
        return this.findAll()
                .stream()
                .map(serviceModel -> {
                    RegionViewModel viewModel = this.mapper.map(serviceModel, RegionViewModel.class);
                    viewModel.setImageUrl(Constants.CLOUDINARY_BASE_URL + serviceModel.getImageUrl());
                    return viewModel;
                })
                .collect(Collectors.toList());
    }
}
