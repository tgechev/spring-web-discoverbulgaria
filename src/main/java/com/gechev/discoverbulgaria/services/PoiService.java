package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.services.models.PoiServiceModel;
import com.gechev.discoverbulgaria.web.models.PoiFormViewModel;

import java.util.List;
import java.util.Set;

public interface PoiService {
    Set<PoiServiceModel> findAll();
    PoiServiceModel findByName(String name);
    void seedPoi(PoiServiceModel[] poiServiceModels);
    List<PoiFormViewModel> getPoiViewModels();
    void addOrEditPoi(PoiFormViewModel poiFormViewModel, boolean isEdit);
    Long getRepositoryCount();
}
