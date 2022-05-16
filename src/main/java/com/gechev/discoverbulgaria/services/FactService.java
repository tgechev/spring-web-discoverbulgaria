package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.data.models.Type;
import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.web.models.CardViewModel;
import com.gechev.discoverbulgaria.web.models.FactFormViewModel;

import java.util.List;

public interface FactService {

    List<FactServiceModel> findAll();

    void seedFacts(FactServiceModel[] factServiceModels);

    void addOrEditFact(FactFormViewModel factFormViewModel, boolean isEdit);

    List<FactFormViewModel> getFactViewModels();

    List<CardViewModel> getFactsByRegionId(String regionId);

    List<CardViewModel> getFactsByRegionIdAndType(String regionId, Type type);

    Long getRepositoryCount();
}
