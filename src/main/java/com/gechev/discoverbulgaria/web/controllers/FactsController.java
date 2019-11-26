package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.CardService;
import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.web.models.AddFactModel;
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
@RequestMapping("/facts")
public class FactsController {

    private final RegionService regionService;
    private final FactService factService;
    private final CardService cardService;

    public FactsController(RegionService regionService, FactService factService, CardService cardService) {
        this.regionService = regionService;
        this.factService = factService;
        this.cardService = cardService;
    }

    @ModelAttribute("addFactModel")
    public AddFactModel model(){
        return new AddFactModel();
    }

    @GetMapping("/add")
    public ModelAndView addFact(@ModelAttribute("addFactModel") AddFactModel addFactModel, ModelAndView modelAndView, HttpServletRequest request){
        request.setAttribute("isPost", false);
        modelAndView.addObject("regionViewModels", regionService.getRegionViewModels());
        modelAndView.setViewName("facts/addOrEdit.html");

        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addFactPost(@Valid @ModelAttribute("addFactModel") AddFactModel addFactModel, BindingResult bindingResult, HttpServletRequest request, ModelAndView modelAndView){
        if(bindingResult.hasErrors()){
            request.setAttribute("isPost", true);
            modelAndView.addObject("regionViewModels", regionService.getRegionViewModels());
            modelAndView.setViewName("facts/addOrEdit.html");
            return modelAndView;
        }

        factService.addFact(addFactModel);

        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView getAllFacts(ModelAndView modelAndView, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<CardViewModel> factsPage = cardService.getCardPageForView("facts", PageRequest.of(currentPage - 1, pageSize), null);
        modelAndView.addObject("cards", factsPage);
        modelAndView.addObject("byRegion", false);
        int totalPages = factsPage.getTotalPages();
        if(totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        modelAndView.setViewName("facts/all.html");
        return modelAndView;
    }

}
