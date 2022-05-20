package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.data.models.Type;
import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.web.models.BaseViewModel;
import com.gechev.discoverbulgaria.web.models.FactFormViewModel;
import com.gechev.discoverbulgaria.web.models.FactViewModel;

import java.util.List;

public interface FactService {

    List<FactServiceModel> findAll();

    void seedFacts(FactServiceModel[] factServiceModels);

  FactViewModel addOrEditFact(FactViewModel factViewModel, boolean isEdit);

    List<FactViewModel> getFactViewModels();

    List<FactViewModel> getFactsByRegionId(String regionId);

    List<FactViewModel> getFactsByRegionIdAndType(String regionId, Type type);

    Long getRepositoryCount();
}
