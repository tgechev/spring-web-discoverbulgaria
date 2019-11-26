package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import com.gechev.discoverbulgaria.web.models.EditRegionModel;
import com.gechev.discoverbulgaria.web.models.RegionViewModel;

import java.io.IOException;
import java.util.List;

public interface RegionService {
    List<RegionServiceModel> findAll();
    RegionServiceModel findByName(String name);
    RegionServiceModel findByRegionId(String regionId);
    void seedRegions(RegionServiceModel[] regionServiceModels) throws IOException;
    boolean editRegion(EditRegionModel editRegionModel);
    List<RegionViewModel> getRegionViewModels();
}
