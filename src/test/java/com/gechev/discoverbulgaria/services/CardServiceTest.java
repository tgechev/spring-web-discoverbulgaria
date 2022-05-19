//package com.gechev.discoverbulgaria.services;
//
//import com.gechev.discoverbulgaria.base.TestBase;
//import com.gechev.discoverbulgaria.data.models.Type;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//public class CardServiceTest extends TestBase {
//
//    @Autowired
//    CardService cardService;
//
//    @Test
//    void getFactCardsForRegion_whenCorrectRegionIdIsGivenAndNoType_shouldReturnCardsOfBothTypesForThisRegion(){
//        String plovdivId = "BG-16";
//        List<CardViewModel> actualCards = this.cardService.getFactCardsForRegion(plovdivId, null);
//
//        actualCards.forEach(cardViewModel -> {
//            Assertions.assertEquals(plovdivId, cardViewModel.getRegionId());
//        });
//    }
//
//    @Test
//    void getFactCardsForRegion_whenCorrectRegionIdAndTypeAreGiven_shouldReturnCorrectCards(){
//        String plovdivId = "BG-16";
//        List<CardViewModel> actualCards = this.cardService.getFactCardsForRegion(plovdivId, Type.HISTORY);
//
//        actualCards.forEach(cardViewModel -> {
//            Assertions.assertEquals(plovdivId, cardViewModel.getRegionId());
//            Assertions.assertEquals(Type.HISTORY, cardViewModel.getType());
//        });
//    }
//}
