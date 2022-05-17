package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.data.models.Type;
import com.gechev.discoverbulgaria.services.models.PoiServiceModel;
import com.gechev.discoverbulgaria.web.models.CardViewModel;
import com.gechev.discoverbulgaria.web.models.PoiFormViewModel;

import java.util.List;

public interface PoiService {
    List<PoiServiceModel> findAll();
    void seedPoi(PoiServiceModel[] poiServiceModels);
    List<CardViewModel> getPoiViewModels();
    void addOrEditPoi(PoiFormViewModel poiFormViewModel, boolean isEdit);
    Long getRepositoryCount();

    List<CardViewModel> getPoiByRegionId(String regionId);

    List<CardViewModel> getPoiByRegionIdAndType(String regionId, Type type);
}
