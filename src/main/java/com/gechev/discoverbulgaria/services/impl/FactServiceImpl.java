package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.data.models.Fact;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.repositories.FactRepository;
import com.gechev.discoverbulgaria.data.repositories.RegionRepository;
import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.ValidationService;
import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import com.gechev.discoverbulgaria.web.models.AddFactModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FactServiceImpl implements FactService {

    private final ValidationService validationService;
    private final ModelMapper mapper;
    private final FactRepository factRepository;
    private final RegionRepository regionRepository;

    public FactServiceImpl(ValidationService validationService, ModelMapper mapper, FactRepository factRepository, RegionRepository regionRepository) {
        this.validationService = validationService;
        this.mapper = mapper;
        this.factRepository = factRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    @Transactional
    public Set<FactServiceModel> findAll(){
        return this.factRepository.findAll()
                .stream()
                .map(r -> this.mapper.map(r, FactServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public FactServiceModel findByType(String type) {
        return null;
    }

    @Override
    public FactServiceModel findByRegion(RegionServiceModel region) {
        return null;
    }

    @Override
    public FactServiceModel findByRegionAndType(RegionServiceModel region, String type) {
        return null;
    }

    @Override
    public void addFact(AddFactModel addFactModel) {
        Fact fact = mapper.map(addFactModel, Fact.class);
        Region region = regionRepository.findByRegionId(addFactModel.getRegionId()).orElseThrow();
        fact.setRegion(region);
        factRepository.save(fact);
    }

    @Override
    @Transactional
    public void seedFacts(FactServiceModel[] factServiceModels) {
        for (FactServiceModel factServiceModel : factServiceModels) {
            //Validate fact model and print message if not valid
            if(!validationService.isValid(factServiceModel)){
                this.validationService.violations(factServiceModel)
                        .forEach(v-> System.out.println(String.format("%s %s", v.getMessage(), v.getInvalidValue())));
                continue;
            }
            try {
                Region region = this.regionRepository.findByRegionId(factServiceModel.getRegion().getRegionId()).orElseThrow();
                Fact fact = this.mapper.map(factServiceModel, Fact.class);
                fact.setRegion(region);
                this.factRepository.saveAndFlush(fact);

            }
            catch(NoSuchElementException e){
                System.out.println(String.format("Fact not added, could not find region with regionId: %s", factServiceModel.getRegion().getRegionId()));
            }
        }
    }
}
