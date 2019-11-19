package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.repositories.RegionRepository;
import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.services.ValidationService;
import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import com.gechev.discoverbulgaria.web.models.EditRegionModel;
import com.gechev.discoverbulgaria.web.models.RegionViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements RegionService {

    private  final RegionRepository regionRepository;
    private final ModelMapper mapper;
    private final ValidationService validationService;

    public RegionServiceImpl(RegionRepository regionRepository, ModelMapper mapper, ValidationService validationService) {
        this.regionRepository = regionRepository;
        this.mapper = mapper;
        this.validationService = validationService;
    }

    @Override
    @Transactional
    public Set<RegionServiceModel> findAll() {
        return this.regionRepository.findAll()
                .stream()
                .map(r -> this.mapper.map(r, RegionServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RegionServiceModel findByName(String name) {
        return this.mapper.map(this.regionRepository.findByName(name), RegionServiceModel.class);
    }

    @Override
    public RegionServiceModel findByRegionId(String regionId){
        return this.mapper.map(this.regionRepository.findByRegionId(regionId), RegionServiceModel.class);
    }

    @Override
    public void seedRegions(RegionServiceModel[] regionServiceModels) {
        for (RegionServiceModel regionServiceModel : regionServiceModels) {
            //Validate region model and print message if not valid
            if(!validationService.isValid(regionServiceModel)){
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
                this.regionRepository.saveAndFlush(region);
                System.out.println(String.format("Region %s successfully created.", region.getName()));
            }
        }
    }

    @Override
    public boolean editRegion(EditRegionModel editRegionModel) {

        RegionServiceModel serviceModel = mapper.map(editRegionModel, RegionServiceModel.class);

        if(!validationService.isValid(serviceModel)){
            //throw error
            validationService.violations(serviceModel).forEach(v-> System.out.println(String.format("%s %s", v.getMessage(), v.getInvalidValue())));
            return false;
        }

        Optional<Region> regionOptional = regionRepository.findByRegionId(editRegionModel.getTheId());

        if(regionOptional.isEmpty()){
            return false;
        }

        Region regionToUpdate = regionOptional.get();

        regionToUpdate.setName(serviceModel.getName());
        regionToUpdate.setRegionId(serviceModel.getRegionId());
        regionToUpdate.setArea(serviceModel.getArea());
        regionToUpdate.setPopulation(serviceModel.getPopulation());

        regionRepository.save(regionToUpdate);

        return true;
    }

    @Override
    public Set<RegionViewModel> getRegionViewModels(){
        return this.regionRepository.findAll()
                .stream()
                .map(r-> mapper.map(r, RegionViewModel.class))
                .collect(Collectors.toSet());
    }
}
