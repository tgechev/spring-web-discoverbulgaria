package com.gechev.discoverbulgaria.services.impl;

import com.gechev.discoverbulgaria.constants.Constants;
import com.gechev.discoverbulgaria.data.models.Type;
import com.gechev.discoverbulgaria.events.FactEvent;
import com.gechev.discoverbulgaria.events.PoiEvent;
import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.CardService;
import com.gechev.discoverbulgaria.services.PoiService;
import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.services.models.PoiServiceModel;
import com.gechev.discoverbulgaria.web.models.CardViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CardServiceImpl implements CardService {

    private final FactService factService;
    private final PoiService poiService;
    private final ModelMapper mapper;
    private List<CardViewModel> factCards;
    private List<CardViewModel> poiCards;

    public CardServiceImpl(FactService factService, PoiService poiService, ModelMapper mapper) {
        this.factService = factService;
        this.poiService = poiService;
        this.mapper = mapper;
        this.factCards = loadFactCards();
        this.poiCards = loadPoiCards();
    }

    @Override
    public Page<CardViewModel> getCardPageForView(String viewName, Pageable pageable, List<CardViewModel> cards) {
        List<CardViewModel> cardsToUse = new ArrayList<>();
        switch (viewName) {
            case "facts":
                cardsToUse = this.factCards;
                break;
            case "home":
                cardsToUse = cards;
                break;
            case "poi":
                cardsToUse = this.poiCards;
                break;
        }

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<CardViewModel> list;

        if(cardsToUse.size() < startItem){
            list = Collections.emptyList();
        } else{
            int toIndex = Math.min(startItem + pageSize, cardsToUse.size());
            list = cardsToUse.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), cardsToUse.size());
    }

    @Override
    public List<Integer> getPageNumbers(Page<CardViewModel> page) {
        List<Integer> pageNumbers = new ArrayList<>();
        int totalPages = page.getTotalPages();
        if(totalPages > 0){
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }

        return pageNumbers;
    }

    @Override
    public Page<CardViewModel> getHomeCards(String regionId, String cat, String stringType, Pageable pageable) {
        stringType = stringType.toUpperCase();
        Type type = null;
        if(!stringType.equals("NULL")){
            type = Type.valueOf(stringType);
        }

        List<CardViewModel> cards = new ArrayList<>();

        switch (cat){
            case "poi":
                cards.addAll(getPoiCardsForRegion(regionId, type));
            break;
            case "facts":
                cards.addAll(getFactCardsForRegion(regionId, type));
            break;
            default:
                cards.addAll(getPoiCardsForRegion(regionId, type));
                cards.addAll(getFactCardsForRegion(regionId, type));
        }

        cards = cards.stream()
                .sorted(Comparator.comparing(CardViewModel::getTitle))
                .collect(Collectors.toList());

        return getCardPageForView("home", pageable, cards);

    }

    private List<CardViewModel> getFactCardsForRegion(String regionId, Type type){
        List<CardViewModel> regionFactCards = new ArrayList<>();

        this.factCards.forEach(card->{
            if(type != null){
                if(card.getRegionId().equals(regionId) && card.getType() == type){
                    regionFactCards.add(card);
                }
            }
            else if(card.getRegionId().equals(regionId)){
                regionFactCards.add(card);
            }

        });

        return regionFactCards;
    }

    private List<CardViewModel> getPoiCardsForRegion(String regionId, Type type){
        List<CardViewModel> regionPoiCards = new ArrayList<>();

        this.poiCards.forEach(cardViewModel -> {
            if(type != null){
                if(cardViewModel.getRegionId().equals(regionId) && cardViewModel.getType() == type){
                    regionPoiCards.add(cardViewModel);
                }
            }
            else if(cardViewModel.getRegionId().equals(regionId)){
                regionPoiCards.add(cardViewModel);
            }
        });

        return regionPoiCards;
    }

    private List<CardViewModel> loadFactCards(){
        return this.factService.findAll().stream()
                .sorted(Comparator.comparing(FactServiceModel::getTitle))
                .map(f->{
                    CardViewModel factCard = this.mapper.map(f, CardViewModel.class);
                    factCard.setRegionId(f.getRegion().getRegionId());
                    factCard.setAddress("no-data");
                    factCard.setImageUrl(Constants.CLOUDINARY_BASE_URL + f.getImageUrl());
                    return factCard;
                })
                .collect(Collectors.toList());
    }

    private List<CardViewModel> loadPoiCards(){
        return this.poiService.findAll().stream()
                .sorted(Comparator.comparing(PoiServiceModel::getTitle))
                .map(poiServiceModel -> {
                    CardViewModel poiCard = this.mapper.map(poiServiceModel, CardViewModel.class);
                    poiCard.setLatitude(poiServiceModel.getCoordinates().getLatitude());
                    poiCard.setLongitude(poiServiceModel.getCoordinates().getLongitude());
                    poiCard.setRegionId(poiServiceModel.getRegion().getRegionId());
                    poiCard.setImageUrl(Constants.CLOUDINARY_BASE_URL + poiServiceModel.getImageUrl());
                    if(poiCard.getAddress().equals("")){
                        poiCard.setAddress("no-data");
                    }
                    return  poiCard;
                })
                .collect(Collectors.toList());
    }

    @Async
    @EventListener
    public void refreshFactCards(FactEvent factEvent){
        this.factCards = loadFactCards();
    }

    @Async
    @EventListener
    public void refreshPoiCards(PoiEvent poiEvent){
        this.poiCards = loadPoiCards();
    }
}
