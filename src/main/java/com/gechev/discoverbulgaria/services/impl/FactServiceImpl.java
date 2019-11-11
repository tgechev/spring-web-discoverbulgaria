package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.data.models.Fact;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.repositories.FactRepository;
import com.gechev.discoverbulgaria.data.repositories.RegionRepository;
import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import com.gechev.discoverbulgaria.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
public class FactServiceImpl implements FactService {

    private final ValidatorUtil validatorUtil;
    private final ModelMapper mapper;
    private final FactRepository factRepository;
    private final RegionRepository regionRepository;

    public FactServiceImpl(ValidatorUtil validatorUtil, ModelMapper mapper, FactRepository factRepository, RegionRepository regionRepository) {
        this.validatorUtil = validatorUtil;
        this.mapper = mapper;
        this.factRepository = factRepository;
        this.regionRepository = regionRepository;
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
    @Transactional
    public void seedFacts(FactServiceModel[] factServiceModels) {
        for (FactServiceModel factServiceModel : factServiceModels) {
            //Validate fact model and print message if not valid
            if(!validatorUtil.isValid(factServiceModel)){
                this.validatorUtil.violations(factServiceModel)
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
