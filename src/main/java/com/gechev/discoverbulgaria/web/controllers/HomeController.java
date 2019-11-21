package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.constants.Constants;
import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.HomeService;
import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.web.models.RegionViewModel;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final RegionService regionService;
    private final ModelMapper mapper;
    private final HomeService homeService;

    public HomeController(RegionService regionService, ModelMapper mapper, HomeService homeService) {
        this.regionService = regionService;
        this.mapper = mapper;
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String getIndex(){
        return "home/index.html";
    }

    @GetMapping(value = "/home")
    public ModelAndView getHome(ModelAndView modelAndView){

        Set<RegionViewModel> regionViewModels = this.regionService.findAll()
                .stream()
                .map(serviceModel -> {
                    RegionViewModel viewModel = mapper.map(serviceModel, RegionViewModel.class);

                    viewModel.setImageUrl(Constants.CLOUDINARY_BASE_URL + serviceModel.getImageUrl());

                    return viewModel;
                })
                .collect(Collectors.toSet());

        modelAndView.addObject("regions", regionViewModels);
        modelAndView.setViewName("home/home.html");
        return modelAndView;
    }

    @GetMapping(value = "/home/{regionId}/{cat}/{type}")
    public ModelAndView getCardsForRegion(@PathVariable String regionId, @PathVariable String cat, @PathVariable String type, ModelAndView modelAndView){

        List cards = homeService.getCards(regionId, cat, type);

        modelAndView.addObject("cards", cards);
        modelAndView.setViewName("fragments/cardFrag.html");

        return modelAndView;
    }
}
