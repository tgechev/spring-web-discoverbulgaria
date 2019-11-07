package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import org.springframework.stereotype.Service;

@Service
public class FactServiceImpl implements FactService {
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
}
