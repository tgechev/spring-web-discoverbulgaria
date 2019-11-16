package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.web.models.RegionViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final RegionService regionService;
    private final ModelMapper mapper;

    public HomeController(RegionService regionService, ModelMapper mapper) {
        this.regionService = regionService;
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
}
