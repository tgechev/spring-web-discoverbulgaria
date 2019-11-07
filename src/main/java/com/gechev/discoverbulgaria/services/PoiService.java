package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.services.models.PoiServiceModel;

import java.util.Set;

public interface PoiService {
    Set<PoiServiceModel> findAll();
    PoiServiceModel findByName(String name);
}
