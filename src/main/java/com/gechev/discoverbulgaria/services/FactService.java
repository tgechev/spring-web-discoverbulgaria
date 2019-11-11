package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.services.models.RegionServiceModel;

public interface FactService {
    FactServiceModel findByType(String type);
    FactServiceModel findByRegion(RegionServiceModel region);
    FactServiceModel findByRegionAndType(RegionServiceModel region, String type);
    void seedFacts(FactServiceModel[] factServiceModels);
}
