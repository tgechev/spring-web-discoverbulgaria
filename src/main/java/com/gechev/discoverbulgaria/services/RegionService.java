package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import com.gechev.discoverbulgaria.web.models.RegionViewModel;

import java.io.IOException;
import java.util.List;

public interface RegionService {
    List<RegionServiceModel> findAll();
    void seedRegions(RegionServiceModel[] regionServiceModels) throws IOException;
    boolean editRegion(RegionViewModel editRegionModel);
    List<RegionViewModel> getRegionViewModels();
    Long getRepositoryCount();
}
