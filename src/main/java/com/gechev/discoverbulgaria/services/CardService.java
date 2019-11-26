package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.events.FactEvent;
import com.gechev.discoverbulgaria.events.PoiEvent;
import com.gechev.discoverbulgaria.web.models.CardViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CardService {
    Page<CardViewModel> getHomeCards(String regionId, String cat, String stringType, Pageable pageable);
    Page<CardViewModel> getCardPageForView(String view, Pageable pageable, List<CardViewModel> cards);
    void refreshFactCards(FactEvent factEvent);
    void refreshPoiCards(PoiEvent poiEvent);
}
