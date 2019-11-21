package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.constants.Constants;
import com.gechev.discoverbulgaria.data.models.Type;
import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.HomeService;
import com.gechev.discoverbulgaria.services.PoiService;
import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.web.models.FactViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements HomeService {

    private final RegionService regionService;
    private final FactService factService;
    private final PoiService poiService;
    private final ModelMapper mapper;

    public HomeServiceImpl(RegionService regionService, FactService factService, PoiService poiService, ModelMapper mapper) {
        this.regionService = regionService;
        this.factService = factService;
        this.poiService = poiService;
        this.mapper = mapper;
    }

    @Override
    public List getCards(String regionId, String cat, String stringType) {
        stringType = stringType.toUpperCase();
        Type type = null;
        if(!stringType.equals("NULL")){
            type = Type.valueOf(stringType);
        }

        List cards = new ArrayList();

        switch (cat){
            case "all":
            case "poi":
            case "facts": cards.addAll(getFactViewModels(regionId, type));
        }

        return cards;
    }

    private List getFactViewModels(String regionId, Type type){
        if(type != null){
            return factService.findAllByRegionAndType(regionId, type)
                    .stream().map(factServiceModel -> {
                        FactViewModel viewModel = mapper.map(factServiceModel, FactViewModel.class);
                        viewModel.setImageUrl(Constants.CLOUDINARY_BASE_URL + factServiceModel.getImageUrl());
                        return viewModel;
                }).collect(Collectors.toList());
        }

        return factService.findAllByRegionId(regionId)
                .stream().map(factServiceModel -> {
                    FactViewModel viewModel = mapper.map(factServiceModel, FactViewModel.class);
                    viewModel.setImageUrl(Constants.CLOUDINARY_BASE_URL + factServiceModel.getImageUrl());
                    return viewModel;
                }).collect(Collectors.toList());
    }
}
