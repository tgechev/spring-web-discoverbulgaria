package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.exceptions.FactNotFoundException;
import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.CardService;
import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.web.models.FactFormViewModel;
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
@RequestMapping("/facts")
public class FactsController extends BaseController {

    private final RegionService regionService;
    private final FactService factService;
    private final CardService cardService;

    public FactsController(RegionService regionService, FactService factService, CardService cardService) {
        this.regionService = regionService;
        this.factService = factService;
        this.cardService = cardService;
    }

    @ModelAttribute("factFormViewModel")
    public FactFormViewModel model(){
        return new FactFormViewModel();
    }

    @GetMapping("/add")
    public ModelAndView addFact(@ModelAttribute("factFormViewModel") FactFormViewModel factFormViewModel, ModelAndView modelAndView){
        modelAndView.addObject("isEdit", false);
        return addOrEditFact(modelAndView);
    }

    @GetMapping("/edit")
    public ModelAndView editFact(@ModelAttribute("factFormViewModel") FactFormViewModel factFormViewModel, ModelAndView modelAndView){
        modelAndView.addObject("isEdit", true);
        modelAndView.addObject("factViewModels", this.factService.getFactViewModels());
        return addOrEditFact(modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addPoiPost(@Valid @ModelAttribute("factFormViewModel") FactFormViewModel factFormViewModel, BindingResult bindingResult, ModelAndView modelAndView){
        modelAndView.addObject("isEdit", false);
        return addOrEditFactPost(factFormViewModel, bindingResult, modelAndView);
    }

    @PostMapping("/edit")
    public ModelAndView editPoiPost(@Valid @ModelAttribute("factFormViewModel") FactFormViewModel factFormViewModel, BindingResult bindingResult, ModelAndView modelAndView){
        modelAndView.addObject("isEdit", true);
        modelAndView.addObject("poiViewModels", this.factService.getFactViewModels());
        return addOrEditFactPost(factFormViewModel, bindingResult, modelAndView);
    }

    private ModelAndView addOrEditFact(ModelAndView modelAndView){
        modelAndView.addObject("isPost", false);
        modelAndView.addObject("isFact", true);
        modelAndView.addObject("editRegion", false);
        modelAndView.addObject("regionViewModels", this.regionService.getRegionViewModels());

        return super.view("facts/addOrEdit.html", modelAndView);
    }

    private ModelAndView addOrEditFactPost(FactFormViewModel factFormViewModel, BindingResult bindingResult, ModelAndView modelAndView){
        modelAndView.addObject("isPost", true);
        modelAndView.addObject("isFact", true);
        modelAndView.addObject("editRegion", false);
        modelAndView.addObject("regionViewModels", this.regionService.getRegionViewModels());

        if(!bindingResult.hasErrors()){
            modelAndView.addObject("isSuccess", true);
            boolean isEdit = (boolean) modelAndView.getModel().get("isEdit");
            this.factService.addOrEditFact(factFormViewModel, isEdit);
        }

        return super.view("facts/addOrEdit.html", modelAndView);
    }

    @GetMapping("/all")
    public ModelAndView getAllFacts(ModelAndView modelAndView, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<CardViewModel> factsPage = this.cardService.getCardPageForView("facts", PageRequest.of(currentPage - 1, pageSize), null);
        List<Integer> pageNumbers = this.cardService.getPageNumbers(factsPage);

        modelAndView.addObject("cards", factsPage);
        modelAndView.addObject("byRegion", false);
        modelAndView.addObject("pageNumbers", pageNumbers);

        return super.view("facts/all.html", modelAndView);
    }

    @GetMapping("/json")
    @ResponseBody
    public List<FactFormViewModel> getFactJson(){
        return this.factService.getFactViewModels();
    }

    @ExceptionHandler(FactNotFoundException.class)
    public ModelAndView handleFactNotFound(FactNotFoundException ex){
        FactFormViewModel factFormViewModel = new FactFormViewModel();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("factFormViewModel", factFormViewModel);
        modelAndView.addObject("isEdit", true);
        modelAndView.addObject("editRegion", false);
        modelAndView.addObject("factViewModels", this.factService.getFactViewModels());

        modelAndView.addObject("isPost", false);
        modelAndView.addObject("isFact", true);
        modelAndView.addObject("regionViewModels", this.regionService.getRegionViewModels());
        modelAndView.addObject("factError", ex.getMessage());

        return super.view("facts/addOrEdit.html", modelAndView);
    }

}
