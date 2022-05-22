package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.data.models.Type;
import com.gechev.discoverbulgaria.services.models.PoiServiceModel;
import com.gechev.discoverbulgaria.web.models.BaseViewModel;
import com.gechev.discoverbulgaria.web.models.DeleteModel;
import com.gechev.discoverbulgaria.web.models.PoiViewModel;

import java.util.List;

public interface PoiService {
  List<PoiServiceModel> findAll();

  void seedPoi(PoiServiceModel[] poiServiceModels);

  List<PoiViewModel> getPoiViewModels();

  PoiViewModel addOrEditPoi(PoiViewModel poiViewModel, boolean isEdit);

  Long getRepositoryCount();

  DeleteModel deletePoi(BaseViewModel poiDeleteModel);

  List<PoiViewModel> getPoiByRegionId(String regionId);

  List<PoiViewModel> getPoiByRegionIdAndType(String regionId, Type type);
}
