package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.dto.ResponseData;
import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.web.models.RegionViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionsController {

    private final RegionService regionService;

    public RegionsController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/all")
    public List<RegionViewModel> getAllRegions(){
        return this.regionService.getRegionViewModels();
    }

    @PostMapping("/edit")
    public ResponseEntity<ResponseData> editRegion(@RequestBody RegionViewModel editRegionModel/*, BindingResult bindingResult, ModelAndView modelAndView*/){
      boolean success = this.regionService.editRegion(editRegionModel);
      if (success) {
        return ResponseEntity.ok(new ResponseData("200", "Region edited."));
      }
      return ResponseEntity.status(500).body(new ResponseData("500", "Region not edited."));

//        if(!bindingResult.hasErrors() && this.regionService.editRegion(editRegionModel)){
//            modelAndView.addObject("isSuccess", true);
//        }

        // return super.view("regions/edit.html", modelAndView);
    }
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
