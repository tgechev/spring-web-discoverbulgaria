//package com.gechev.discoverbulgaria.services;
//
//import com.gechev.discoverbulgaria.data.models.Type;
//import com.gechev.discoverbulgaria.events.FactEvent;
//import com.gechev.discoverbulgaria.events.PoiEvent;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
//public interface CardService {
//    Page<CardViewModel> getHomeCards(String regionId, String cat, String stringType, Pageable pageable);
//    Page<CardViewModel> getCardPageForView(String view, Pageable pageable, List<CardViewModel> cards);
//    void refreshFactCards(FactEvent factEvent);
//    void refreshPoiCards(PoiEvent poiEvent);
//    void refreshCards();
//
//    List<Integer> getPageNumbers(Page<CardViewModel> page);
//
//    List<CardViewModel> getFactsByRegion(String regionId, Type type);
//
//    List<CardViewModel> getPoiCardsForRegion(String regionId, Type type);
//}
