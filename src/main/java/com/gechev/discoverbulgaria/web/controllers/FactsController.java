//package com.gechev.discoverbulgaria.web.controllers;
//
//import com.gechev.discoverbulgaria.constants.Constants;
//import com.gechev.discoverbulgaria.exceptions.FactNotFoundException;
//import com.gechev.discoverbulgaria.services.CardService;
//import com.gechev.discoverbulgaria.services.FactService;
//import com.gechev.discoverbulgaria.services.RegionService;
//import com.gechev.discoverbulgaria.web.models.CardViewModel;
//import com.gechev.discoverbulgaria.web.models.FactFormViewModel;
//import com.gechev.discoverbulgaria.web.models.RegionViewModel;
//import org.modelmapper.ModelMapper;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Controller
//public class HomeController extends BaseController {
//
//  private final RegionService regionService;
//  private final CardService cardService;
//
//  public HomeController(RegionService regionService, CardService cardService) {
//    this.regionService = regionService;
//    this.cardService = cardService;
//  }
//
//  @GetMapping("/")
//  public ModelAndView getIndex(){
//    return super.view("home/index.html");
//  }
//
//  @GetMapping(value = "/home")
//  public ModelAndView getHome(ModelAndView modelAndView){
//
//    List<RegionViewModel> regionViewModels = this.regionService.getRegionViewModels();
//    modelAndView.addObject("regions", regionViewModels);
//    return super.view("home/home.html", modelAndView);
//  }
//
//  @GetMapping(value = "/home/{regionId}/{cat}/{type}")
//  public ModelAndView getCardsForRegion(@PathVariable String regionId, @PathVariable String cat, @PathVariable String type, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, ModelAndView modelAndView){
//
//    int currentPage = page.orElse(1);
//    int pageSize = size.orElse(4);
//
//    Page<CardViewModel> cardsPage = this.cardService.getHomeCards(regionId, cat, type, PageRequest.of(currentPage - 1, pageSize));
//    List<Integer> pageNumbers = this.cardService.getPageNumbers(cardsPage);
//
//    modelAndView.addObject("cards", cardsPage);
//    modelAndView.addObject("byRegion", true);
//    modelAndView.addObject("pageNumbers", pageNumbers);
//
//    return super.view("fragments/cardFrag.html", modelAndView);
//  }
//}

package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.data.models.Type;
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

@RestController
@RequestMapping("api/facts")
public class FactsController {

  private final RegionService regionService;
  private final FactService factService;
  private final CardService cardService;

  public FactsController(RegionService regionService, FactService factService, CardService cardService) {
    this.regionService = regionService;
    this.factService = factService;
    this.cardService = cardService;
  }

//  @ModelAttribute("factFormViewModel")
//  public FactFormViewModel model(){
//    return new FactFormViewModel();
//  }

//  @GetMapping("/add")
//  public ModelAndView addFact(@ModelAttribute("factFormViewModel") FactFormViewModel factFormViewModel, ModelAndView modelAndView){
//    modelAndView.addObject("isEdit", false);
//    return addOrEditFact(modelAndView);
//  }
//
//  @GetMapping("/edit")
//  public ModelAndView editFact(@ModelAttribute("factFormViewModel") FactFormViewModel factFormViewModel, ModelAndView modelAndView){
//    modelAndView.addObject("isEdit", true);
//    modelAndView.addObject("factViewModels", this.factService.getFactViewModels());
//    return addOrEditFact(modelAndView);
//  }
//
//  @PostMapping("/add")
//  public ModelAndView addPoiPost(@Valid @ModelAttribute("factFormViewModel") FactFormViewModel factFormViewModel, BindingResult bindingResult, ModelAndView modelAndView){
//    modelAndView.addObject("isEdit", false);
//    return addOrEditFactPost(factFormViewModel, bindingResult, modelAndView);
//  }
//
//  @PostMapping("/edit")
//  public ModelAndView editPoiPost(@Valid @ModelAttribute("factFormViewModel") FactFormViewModel factFormViewModel, BindingResult bindingResult, ModelAndView modelAndView){
//    modelAndView.addObject("isEdit", true);
//    modelAndView.addObject("poiViewModels", this.factService.getFactViewModels());
//    return addOrEditFactPost(factFormViewModel, bindingResult, modelAndView);
//  }
//
//  private ModelAndView addOrEditFact(ModelAndView modelAndView){
//    modelAndView.addObject("isPost", false);
//    modelAndView.addObject("isFact", true);
//    modelAndView.addObject("editRegion", false);
//    modelAndView.addObject("regionViewModels", this.regionService.getRegionViewModels());
//
//    return super.view("facts/addOrEdit.html", modelAndView);
//  }
//
//  private ModelAndView addOrEditFactPost(FactFormViewModel factFormViewModel, BindingResult bindingResult, ModelAndView modelAndView){
//    modelAndView.addObject("isPost", true);
//    modelAndView.addObject("isFact", true);
//    modelAndView.addObject("editRegion", false);
//    modelAndView.addObject("regionViewModels", this.regionService.getRegionViewModels());
//
//    if(!bindingResult.hasErrors()){
//      modelAndView.addObject("isSuccess", true);
//      boolean isEdit = (boolean) modelAndView.getModel().get("isEdit");
//      this.factService.addOrEditFact(factFormViewModel, isEdit);
//    }
//
//    return super.view("facts/addOrEdit.html", modelAndView);
//  }

  @GetMapping("/all")
  public List<FactFormViewModel> getAllFacts() {
    return this.factService.getFactViewModels();
  }

  @GetMapping(value = "/{regionId}")
  public List<CardViewModel> getFactsForRegion(@PathVariable String regionId, @RequestParam Optional<String> type, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
    if (type.isPresent()) {
      return this.factService.getFactsByRegionIdAndType(regionId, Type.valueOf(type.get()));
    } else {
      return this.factService.getFactsByRegionId(regionId);
    }
    // return this.cardService.getFactsByRegion(regionId, type);
    //    int currentPage = page.orElse(1);
//    int pageSize = size.orElse(4);
//
//    Page<CardViewModel> cardsPage = this.cardService.getHomeCards(regionId, cat, type, PageRequest.of(currentPage - 1, pageSize));
//    List<Integer> pageNumbers = this.cardService.getPageNumbers(cardsPage);
  }
}

