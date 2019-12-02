package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.constants.Constants;
import com.gechev.discoverbulgaria.services.CardService;
import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.web.models.CardViewModel;
import com.gechev.discoverbulgaria.web.models.RegionViewModel;
import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController extends BaseController {

    private final RegionService regionService;
    private final CardService cardService;

    public HomeController(RegionService regionService, CardService cardService) {
        this.regionService = regionService;
        this.cardService = cardService;
    }

    @GetMapping("/")
    public ModelAndView getIndex(){
        return super.view("home/index.html");
    }

    @GetMapping(value = "/home")
    public ModelAndView getHome(ModelAndView modelAndView){

        List<RegionViewModel> regionViewModels = this.regionService.getRegionViewModels();
        modelAndView.addObject("regions", regionViewModels);
        return super.view("home/home.html", modelAndView);
    }

    @GetMapping(value = "/home/{regionId}/{cat}/{type}")
    public ModelAndView getCardsForRegion(@PathVariable String regionId, @PathVariable String cat, @PathVariable String type, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, ModelAndView modelAndView){

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(4);

        Page<CardViewModel> cardsPage = this.cardService.getHomeCards(regionId, cat, type, PageRequest.of(currentPage - 1, pageSize));
        List<Integer> pageNumbers = this.cardService.getPageNumbers(cardsPage);

        modelAndView.addObject("cards", cardsPage);
        modelAndView.addObject("byRegion", true);
        modelAndView.addObject("pageNumbers", pageNumbers);

        return super.view("fragments/cardFrag.html", modelAndView);
    }
}
