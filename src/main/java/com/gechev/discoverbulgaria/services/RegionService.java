package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.services.models.RegionServiceModel;

import java.util.Set;

public interface RegionService {
    Set<RegionServiceModel> findAll();
    RegionServiceModel findByName(String name);
}
