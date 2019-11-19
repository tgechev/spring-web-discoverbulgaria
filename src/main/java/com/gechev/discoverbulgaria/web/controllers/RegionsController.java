package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.web.models.EditRegionModel;
import com.gechev.discoverbulgaria.web.models.RegionViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/regions")
public class RegionsController {

    private final RegionService regionService;

    public RegionsController(RegionService regionService) {
        this.regionService = regionService;
    }

    @ModelAttribute("editRegionModel")
    public EditRegionModel model(){
        return new EditRegionModel();
    }

    @GetMapping("/edit")
    public ModelAndView editRegion(ModelAndView modelAndView, @ModelAttribute("editRegionModel") EditRegionModel editRegionModel, HttpServletRequest request){
        request.setAttribute("isPost", false);
        modelAndView.addObject("regionViewModels", regionService.getRegionViewModels());
        modelAndView.setViewName("regions/edit.html");
        return modelAndView;
    }

    @GetMapping("/json")
    @ResponseBody
    public Set<RegionViewModel> getRegionsJson(){
        return regionService.getRegionViewModels();
    }

    @PostMapping("/edit")
    public ModelAndView editRegionPost(@Valid @ModelAttribute("editRegionModel") EditRegionModel editRegionModel, BindingResult bindingResult, HttpServletRequest request, ModelAndView modelAndView){
        if(bindingResult.hasErrors() || !regionService.editRegion(editRegionModel)){
            request.setAttribute("isPost", true);
            modelAndView.addObject("regionViewModels", regionService.getRegionViewModels());
            modelAndView.setViewName("regions/edit.html");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }
}
