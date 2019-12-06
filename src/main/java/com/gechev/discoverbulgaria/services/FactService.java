package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.data.models.Type;
import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.web.models.FactFormViewModel;

import java.util.List;
import java.util.Set;

public interface FactService {
    List<FactServiceModel> findAll();
    FactServiceModel findByType(String type);
    Set<FactServiceModel> findAllByRegionId(String regionId);
    Set<FactServiceModel> findAllByRegionAndType(String regionId, Type type);
    void seedFacts(FactServiceModel[] factServiceModels);
    void addOrEditFact(FactFormViewModel factFormViewModel, boolean isEdit);

    List<FactFormViewModel> getFactViewModels();
    Long getRepositoryCount();
}
