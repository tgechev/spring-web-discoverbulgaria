package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.web.models.FactViewModel;
import com.gechev.discoverbulgaria.web.models.RegionViewModel;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final RegionService regionService;
    private final FactService factService;
    private final ModelMapper mapper;

    public HomeController(RegionService regionService, FactService factService, ModelMapper mapper) {
        this.regionService = regionService;
        this.factService = factService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public String getIndex(){
        return "home/index.html";
    }

    @GetMapping(value = "/home")
    public ModelAndView getHome(ModelAndView modelAndView){

        Set<RegionViewModel> regionViewModels = this.regionService.findAll()
                .stream()
                .map(serviceModel -> mapper.map(serviceModel, RegionViewModel.class))
                .collect(Collectors.toSet());

        modelAndView.addObject("regions", regionViewModels);
        modelAndView.setViewName("home/home.html");
        return modelAndView;
    }

    @GetMapping(value = "/home/{cat}/{type}")
    public ModelAndView getCardsForRegion(@PathVariable String cat, @PathVariable String type, ModelAndView modelAndView){

        Set<FactViewModel> factViewModels = this.factService.findAll()
                .stream()
                .map(factServiceModel -> mapper.map(factServiceModel, FactViewModel.class))
                .collect(Collectors.toSet());

        modelAndView.addObject("facts", factViewModels);
        modelAndView.setViewName("fragments/cardFrag.html");

        return modelAndView;
    }
}
