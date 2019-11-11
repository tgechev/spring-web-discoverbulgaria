package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.data.models.Poi;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.repositories.PoiRepository;
import com.gechev.discoverbulgaria.data.repositories.RegionRepository;
import com.gechev.discoverbulgaria.services.PoiService;
import com.gechev.discoverbulgaria.services.models.PoiServiceModel;
import com.gechev.discoverbulgaria.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class PoiServiceImpl implements PoiService {

    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;
    private final RegionRepository regionRepository;
    private final PoiRepository poiRepository;

    public PoiServiceImpl(ModelMapper mapper, ValidatorUtil validatorUtil, RegionRepository regionRepository, PoiRepository poiRepository) {
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
        this.regionRepository = regionRepository;
        this.poiRepository = poiRepository;
    }

    @Override
    public Set<PoiServiceModel> findAll() {
        return null;
    }

    @Override
    public PoiServiceModel findByName(String name) {
        return null;
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
                Region region = this.regionRepository.findByRegionId(poiServiceModel.getRegion().getRegionId()).orElseThrow();
                Poi poi = this.mapper.map(poiServiceModel, Poi.class);
                poi.setRegion(region);
                this.poiRepository.saveAndFlush(poi);

            }
            catch(NoSuchElementException e){
                System.out.println(String.format("POI not added, could not find region with regionId: %s", poiServiceModel.getRegion().getRegionId()));
            }
        }
    }
}
