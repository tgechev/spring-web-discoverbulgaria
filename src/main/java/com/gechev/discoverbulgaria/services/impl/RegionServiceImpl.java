package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RegionServiceImpl implements RegionService {
    @Override
    public Set<RegionServiceModel> findAll() {
        return null;
    }

    @Override
    public RegionServiceModel findByName(String name) {
        return null;
    }
}
