package com.gechev.discoverbulgaria.web.controllers;

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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        modelAndView.addObject("regionViewModels", regionService.getRegionViewModels());
        modelAndView.setViewName("poi/addOrEdit.html");

        return modelAndView;
    }

    private ModelAndView addOrEditPoiPost(PoiFormViewModel poiFormViewModel, BindingResult bindingResult, ModelAndView modelAndView){
        if(bindingResult.hasErrors()){
            modelAndView.addObject("isPost", true);
            modelAndView.addObject("regionViewModels", regionService.getRegionViewModels());
            modelAndView.setViewName("poi/addOrEdit.html");
            return modelAndView;
        }

        boolean isEdit = (boolean) modelAndView.getModel().get("isEdit");
        poiService.addOrEditPoi(poiFormViewModel, isEdit);

        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView getAllPoi(ModelAndView modelAndView, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<CardViewModel> poiPage = cardService.getCardPageForView("poi", PageRequest.of(currentPage - 1, pageSize), null);
        modelAndView.addObject("cards", poiPage);
        modelAndView.addObject("byRegion", false);
        modelAndView.addObject("isPoi", true);
        int totalPages = poiPage.getTotalPages();
        if(totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        modelAndView.setViewName("poi/all.html");
        return modelAndView;
    }

    @GetMapping("/json")
    @ResponseBody
    public List<PoiFormViewModel> getRegionsJson(){
        return poiService.getPoiViewModels();
    }
}
