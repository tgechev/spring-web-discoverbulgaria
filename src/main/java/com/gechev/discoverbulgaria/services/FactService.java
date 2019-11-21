package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.data.models.Type;
import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import com.gechev.discoverbulgaria.web.models.AddFactModel;

import java.util.Set;

public interface FactService {
    Set<FactServiceModel> findAll();
    FactServiceModel findByType(String type);
    Set<FactServiceModel> findAllByRegionId(String regionId);
    Set<FactServiceModel> findAllByRegionAndType(String regionId, Type type);
    void seedFacts(FactServiceModel[] factServiceModels);
    void addFact(AddFactModel addFactModel);
}
