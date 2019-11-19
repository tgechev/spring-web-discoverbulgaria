package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.web.models.AddFactModel;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/facts")
public class FactsController {

    private final RegionService regionService;
    private final FactService factService;

    public FactsController(RegionService regionService, FactService factService) {
        this.regionService = regionService;
        this.factService = factService;
    }

    @ModelAttribute("addFactModel")
    public AddFactModel model(){
        return new AddFactModel();
    }

    @GetMapping("/add")
    public ModelAndView addFact(@ModelAttribute("addFactModel") AddFactModel addFactModel, ModelAndView modelAndView, HttpServletRequest request){
        request.setAttribute("isPost", false);
        modelAndView.addObject("regionViewModels", regionService.getRegionViewModels());
        modelAndView.setViewName("facts/add.html");

        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addFactPost(@Valid @ModelAttribute("addFactModel") AddFactModel addFactModel, BindingResult bindingResult, HttpServletRequest request, ModelAndView modelAndView){
        if(bindingResult.hasErrors()){
            request.setAttribute("isPost", true);
            modelAndView.addObject("regionViewModels", regionService.getRegionViewModels());
            modelAndView.setViewName("facts/add.html");
            return modelAndView;
        }

        factService.addFact(addFactModel);

        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

}
