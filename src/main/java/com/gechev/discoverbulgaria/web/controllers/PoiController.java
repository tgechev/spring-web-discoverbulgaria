package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.exceptions.PoiNotFoundException;
import com.gechev.discoverbulgaria.services.CardService;
import com.gechev.discoverbulgaria.services.PoiService;
import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.web.models.PoiFormViewModel;
import com.gechev.discoverbulgaria.web.models.CardViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/poi")
public class PoiController {

    private final PoiService poiService;
    private final RegionService regionService;
    private final CardService cardService;

    public PoiController(PoiService poiService, RegionService regionService, CardService cardService) {
        this.poiService = poiService;
        this.regionService = regionService;
        this.cardService = cardService;
    }

    @ModelAttribute("poiFormViewModel")
    public PoiFormViewModel model(){
        return new PoiFormViewModel();
    }

    @GetMapping("/add")
    public ModelAndView addPoi(@ModelAttribute("poiFormViewModel") PoiFormViewModel poiFormViewModel, ModelAndView modelAndView){
        modelAndView.addObject("isEdit", false);
        return addOrEditPoi(modelAndView);
    }

    @GetMapping("/edit")
    public ModelAndView editPoi(@ModelAttribute("poiFormViewModel") PoiFormViewModel poiFormViewModel, ModelAndView modelAndView){
        modelAndView.addObject("isEdit", true);
        modelAndView.addObject("poiViewModels", poiService.getPoiViewModels());
        return addOrEditPoi(modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addPoiPost(@Valid @ModelAttribute("poiFormViewModel") PoiFormViewModel poiFormViewModel, BindingResult bindingResult, ModelAndView modelAndView){
        modelAndView.addObject("isEdit", false);
        return addOrEditPoiPost(poiFormViewModel, bindingResult, modelAndView);
    }

    @PostMapping("/edit")
    public ModelAndView editPoiPost(@Valid @ModelAttribute("poiFormViewModel") PoiFormViewModel poiFormViewModel, BindingResult bindingResult, ModelAndView modelAndView){
        modelAndView.addObject("isEdit", true);
        modelAndView.addObject("poiViewModels", poiService.getPoiViewModels());
        return addOrEditPoiPost(poiFormViewModel, bindingResult, modelAndView);
    }

    private ModelAndView addOrEditPoi(ModelAndView modelAndView){
        modelAndView.addObject("isPost", false);
        modelAndView.addObject("isFact", false);
        modelAndView.addObject("editRegion", false);
        modelAndView.addObject("regionViewModels", regionService.getRegionViewModels());
        modelAndView.setViewName("poi/addOrEdit.html");

        return modelAndView;
    }

    private ModelAndView addOrEditPoiPost(PoiFormViewModel poiFormViewModel, BindingResult bindingResult, ModelAndView modelAndView){

        modelAndView.addObject("isPost", true);
        modelAndView.addObject("isFact", false);
        modelAndView.addObject("editRegion", false);
        modelAndView.addObject("regionViewModels", regionService.getRegionViewModels());
        modelAndView.setViewName("poi/addOrEdit.html");

        if(!bindingResult.hasErrors()){
            modelAndView.addObject("isSuccess", true);
            boolean isEdit = (boolean) modelAndView.getModel().get("isEdit");
            poiService.addOrEditPoi(poiFormViewModel, isEdit);
        }

        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView getAllPoi(ModelAndView modelAndView, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<CardViewModel> poiPage = cardService.getCardPageForView("poi", PageRequest.of(currentPage - 1, pageSize), null);
        List<Integer> pageNumbers = cardService.getPageNumbers(poiPage);

        modelAndView.addObject("cards", poiPage);
        modelAndView.addObject("byRegion", false);
        modelAndView.addObject("isPoi", true);
        modelAndView.addObject("pageNumbers", pageNumbers);

        modelAndView.setViewName("poi/all.html");
        return modelAndView;
    }

    @GetMapping("/json")
    @ResponseBody
    public List<PoiFormViewModel> getPoiJson(){
        return poiService.getPoiViewModels();
    }

    @ExceptionHandler(PoiNotFoundException.class)
    public ModelAndView handlePoiNotFound(PoiNotFoundException ex){
        PoiFormViewModel poiFormViewModel = new PoiFormViewModel();

        ModelAndView modelAndView = new ModelAndView("poi/addOrEdit.html");

        modelAndView.addObject("poiFormViewModel", poiFormViewModel);
        modelAndView.addObject("isEdit", true);
        modelAndView.addObject("editRegion", false);
        modelAndView.addObject("poiViewModels", poiService.getPoiViewModels());

        modelAndView.addObject("isPost", false);
        modelAndView.addObject("isFact", false);
        modelAndView.addObject("regionViewModels", regionService.getRegionViewModels());
        modelAndView.addObject("poiError", ex.getMessage());

        return modelAndView;
    }
}