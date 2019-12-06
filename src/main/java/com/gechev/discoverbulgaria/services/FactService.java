package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.web.models.FactFormViewModel;

import java.util.List;

public interface FactService {

    List<FactServiceModel> findAll();

    void seedFacts(FactServiceModel[] factServiceModels);

    void addOrEditFact(FactFormViewModel factFormViewModel, boolean isEdit);

    List<FactFormViewModel> getFactViewModels();

    Long getRepositoryCount();
}
