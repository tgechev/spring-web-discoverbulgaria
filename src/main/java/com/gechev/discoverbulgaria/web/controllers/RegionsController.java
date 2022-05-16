package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.exceptions.RegionNotFoundException;
import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.web.models.EditRegionModel;
import com.gechev.discoverbulgaria.web.models.RegionViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionsController {

    private final RegionService regionService;

    public RegionsController(RegionService regionService) {
        this.regionService = regionService;
    }

    @ModelAttribute("editRegionModel")
    public EditRegionModel model(){
        return new EditRegionModel();
    }

//    @GetMapping("/edit")
//    public ModelAndView editRegion(ModelAndView modelAndView, @ModelAttribute("editRegionModel") EditRegionModel editRegionModel){
//        modelAndView.addObject("isPost", false);
//        modelAndView.addObject("regionViewModels", this.regionService.getRegionViewModels());
//        modelAndView.addObject("editRegion", true);
//        modelAndView.addObject("isEdit", true);
//
//        return super.view("regions/edit.html", modelAndView);
//    }

    @GetMapping("/all")
    public List<RegionViewModel> getAllRegions(){
        return this.regionService.getRegionViewModels();
    }

//    @PostMapping("/edit")
//    public ModelAndView editRegionPost(@Valid @ModelAttribute("editRegionModel") EditRegionModel editRegionModel, BindingResult bindingResult, ModelAndView modelAndView){
//        modelAndView.addObject("isPost", true);
//        modelAndView.addObject("regionViewModels", this.regionService.getRegionViewModels());
//        modelAndView.addObject("editRegion", true);
//        modelAndView.addObject("isEdit", true);
//
//        if(!bindingResult.hasErrors() && this.regionService.editRegion(editRegionModel)){
//            modelAndView.addObject("isSuccess", true);
//        }
//
//        return super.view("regions/edit.html", modelAndView);
//    }
//
//    @ExceptionHandler(RegionNotFoundException.class)
//    public ModelAndView handleRegionNotFound(RegionNotFoundException exception){
//        EditRegionModel editRegionModel = new EditRegionModel();
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("editRegionModel", editRegionModel);
//        modelAndView.addObject("isPost", false);
//        modelAndView.addObject("regionViewModels", this.regionService.getRegionViewModels());
//        modelAndView.addObject("editRegion", true);
//        modelAndView.addObject("isEdit", true);
//        modelAndView.addObject("regionError", exception.getMessage());
//
//        return super.view("regions/edit.html", modelAndView);
//    }
}
