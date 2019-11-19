package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import com.gechev.discoverbulgaria.web.models.AddFactModel;

import java.util.Set;

public interface FactService {
    Set<FactServiceModel> findAll();
    FactServiceModel findByType(String type);
    FactServiceModel findByRegion(RegionServiceModel region);
    FactServiceModel findByRegionAndType(RegionServiceModel region, String type);
    void seedFacts(FactServiceModel[] factServiceModels);
    void addFact(AddFactModel addFactModel);
}
