package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.data.models.Type;
// import com.gechev.discoverbulgaria.services.CardService;
import com.gechev.discoverbulgaria.services.PoiService;
// import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.web.models.PoiFormViewModel;
import com.gechev.discoverbulgaria.web.models.PoiViewModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/poi")
public class PoiController {

  private final PoiService poiService;
  // private final RegionService regionService;
  // private final CardService cardService;

  public PoiController(PoiService poiService/*, RegionService regionService, CardService cardService*/) {
    this.poiService = poiService;
    // this.regionService = regionService;
    // this.cardService = cardService;
  }

  @ModelAttribute("poiFormViewModel")
  public PoiFormViewModel model() {
    return new PoiFormViewModel();
  }

//    @GetMapping("/add")
//    public ModelAndView addPoi(@ModelAttribute("poiFormViewModel") PoiFormViewModel poiFormViewModel, ModelAndView modelAndView){
//        modelAndView.addObject("isEdit", false);
//        return addOrEditPoi(modelAndView);
//    }
//
//    @GetMapping("/edit")
//    public ModelAndView editPoi(@ModelAttribute("poiFormViewModel") PoiFormViewModel poiFormViewModel, ModelAndView modelAndView){
//        modelAndView.addObject("isEdit", true);
//        modelAndView.addObject("poiViewModels", this.poiService.getPoiViewModels());
//        return addOrEditPoi(modelAndView);
//    }
//
//    @PostMapping("/add")
//    public ModelAndView addPoiPost(@Valid @ModelAttribute("poiFormViewModel") PoiFormViewModel poiFormViewModel, BindingResult bindingResult, ModelAndView modelAndView){
//        modelAndView.addObject("isEdit", false);
//        return addOrEditPoiPost(poiFormViewModel, bindingResult, modelAndView);
//    }
//
//    @PostMapping("/edit")
//    public ModelAndView editPoiPost(@Valid @ModelAttribute("poiFormViewModel") PoiFormViewModel poiFormViewModel, BindingResult bindingResult, ModelAndView modelAndView){
//        modelAndView.addObject("isEdit", true);
//        modelAndView.addObject("poiViewModels", this.poiService.getPoiViewModels());
//        return addOrEditPoiPost(poiFormViewModel, bindingResult, modelAndView);
//    }
//
//    private ModelAndView addOrEditPoi(ModelAndView modelAndView){
//        modelAndView.addObject("isPost", false);
//        modelAndView.addObject("isFact", false);
//        modelAndView.addObject("editRegion", false);
//        modelAndView.addObject("regionViewModels", this.regionService.getRegionViewModels());
//
//        return super.view("poi/addOrEdit.html", modelAndView);
//    }

//    private ModelAndView addOrEditPoiPost(PoiFormViewModel poiFormViewModel, BindingResult bindingResult, ModelAndView modelAndView){
//
//        modelAndView.addObject("isPost", true);
//        modelAndView.addObject("isFact", false);
//        modelAndView.addObject("editRegion", false);
//        modelAndView.addObject("regionViewModels", this.regionService.getRegionViewModels());
//
//        if(!bindingResult.hasErrors()){
//            modelAndView.addObject("isSuccess", true);
//            boolean isEdit = (boolean) modelAndView.getModel().get("isEdit");
//            this.poiService.addOrEditPoi(poiFormViewModel, isEdit);
//        }
//
//        return super.view("poi/addOrEdit.html", modelAndView);
//    }
//
//    @GetMapping("/all")
//    public ModelAndView getAllPoi(ModelAndView modelAndView, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(10);
//
//        Page<CardViewModel> poiPage = this.cardService.getCardPageForView("poi", PageRequest.of(currentPage - 1, pageSize), null);
//        List<Integer> pageNumbers = this.cardService.getPageNumbers(poiPage);
//
//        modelAndView.addObject("cards", poiPage);
//        modelAndView.addObject("byRegion", false);
//        modelAndView.addObject("isPoi", true);
//        modelAndView.addObject("pageNumbers", pageNumbers);
//
//        return super.view("poi/all.html", modelAndView);
//    }

  @GetMapping("/all")
  public List<PoiViewModel> getAllPoi() {
    return this.poiService.getPoiViewModels();
  }

  @GetMapping(value = "/{regionId}")
  public List<PoiViewModel> getPoiForRegion(@PathVariable String regionId, @RequestParam Optional<String> type, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
    if (type.isPresent()) {
      return this.poiService.getPoiByRegionIdAndType(regionId, Type.valueOf(type.get()));
    } else {
      return this.poiService.getPoiByRegionId(regionId);
    }
  }

//    @ExceptionHandler(PoiNotFoundException.class)
//    public ModelAndView handlePoiNotFound(PoiNotFoundException ex){
//        PoiFormViewModel poiFormViewModel = new PoiFormViewModel();
//
//        ModelAndView modelAndView = new ModelAndView();
//
//        modelAndView.addObject("poiFormViewModel", poiFormViewModel);
//        modelAndView.addObject("isEdit", true);
//        modelAndView.addObject("editRegion", false);
//        modelAndView.addObject("poiViewModels", poiService.getPoiViewModels());
//
//        modelAndView.addObject("isPost", false);
//        modelAndView.addObject("isFact", false);
//        modelAndView.addObject("regionViewModels", this.regionService.getRegionViewModels());
//        modelAndView.addObject("poiError", ex.getMessage());
//
//        return super.view("poi/addOrEdit.html", modelAndView);
//    }
}
