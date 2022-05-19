package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.base.TestBase;
import com.gechev.discoverbulgaria.data.models.Fact;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.models.Type;
import com.gechev.discoverbulgaria.data.repositories.FactRepository;
import com.gechev.discoverbulgaria.exceptions.FactNotFoundException;
import com.gechev.discoverbulgaria.exceptions.RegionNotFoundException;
import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import com.gechev.discoverbulgaria.web.models.BaseViewModel;
import com.gechev.discoverbulgaria.web.models.FactFormViewModel;
import com.gechev.discoverbulgaria.web.models.FactViewModel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FactServiceTest extends TestBase {

    @MockBean
    public ValidationService validationService;

    @MockBean
    FactRepository factRepository;

    @MockBean
    ApplicationEventPublisher eventPublisher;

    @Autowired
    FactService factService;

    @Captor
    ArgumentCaptor<Fact> argCaptor;

    @Test
    void findAll_whenThreeUnorderedFacts_shouldReturnServiceModelsSorted() {

        List<Fact> facts = new ArrayList<>();

        Fact fact1 = new Fact();
        fact1.setTitle("България");

        Fact fact2 = new Fact();
        fact2.setTitle("Столица");

        Fact fact3 = new Fact();
        fact3.setTitle("Открития");

        facts.add(fact2);
        facts.add(fact1);
        facts.add(fact3);



        Mockito.when(factRepository.findAll()).thenReturn(facts);

        List<FactServiceModel> actualFacts = factService.findAll();

        assertEquals(facts.size(), actualFacts.size(), "Sizes do not match");
        assertEquals(facts.get(0).getTitle(), actualFacts.get(2).getTitle());
        assertEquals(facts.get(1).getTitle(), actualFacts.get(0).getTitle());
        assertEquals(facts.get(2).getTitle(), actualFacts.get(1).getTitle());
    }

    @Test
    void getFactViewModels_whenThreeUnorderedFacts_shouldReturnViewModelsSorted() {

        List<Fact> facts = new ArrayList<>();

        Fact fact1 = new Fact();
        fact1.setTitle("България");
        fact1.setRegion(new Region("BG-01", "Blagoevgrad", 32323, 2449.0, "BlagoevgradUrl", null, null));

        Fact fact2 = new Fact();
        fact2.setTitle("Столица");
        fact2.setRegion(new Region("BG-02", "Burgas", 32323, 2449.0, "BurgasUrl", null, null));

        Fact fact3 = new Fact();
        fact3.setTitle("Открития");
        fact3.setRegion(new Region("BG-03", "Varna", 32323, 2449.0, "VarnaUrl", null, null));

        facts.add(fact2);
        facts.add(fact1);
        facts.add(fact3);



        Mockito.when(factRepository.findAll()).thenReturn(facts);

        List<FactViewModel> actualFacts = factService.getFactViewModels();

        assertEquals(facts.size(), actualFacts.size(), "Sizes do not match");
        assertEquals(facts.get(0).getTitle(), actualFacts.get(2).getTitle());
        assertEquals(facts.get(0).getRegion().getRegionId(), actualFacts.get(2).getRegionId());
        assertEquals(facts.get(1).getTitle(), actualFacts.get(0).getTitle());
        assertEquals(facts.get(2).getTitle(), actualFacts.get(1).getTitle());
    }

    @Test
    public void addOrEditFat_whenCompleteFactIsPassed_shouldSaveCorrectly(){

        FactFormViewModel factViewModel1 = new FactFormViewModel("Fact1", "oldFact1", Type.NATURE, "BG-16", "This is my first add fact test", "myFirstFactTestUrl", "Please go to Google to read more facts");

        Fact factByTitle = new Fact("Fact1", "old description", Type.HISTORY, "someUrl", "do not read more", "test-video-id", null);
        Optional factByTitleOpt = Optional.of(factByTitle);
        Region regionToReturn = new Region("BG-16", "Plovediv", 333000, 232323.3232, "plovdivUrl", null, null);
        Optional regionToReturnOpt = Optional.of(regionToReturn);

        Mockito.when(factRepository.findByTitle(factViewModel1.getOldTitle())).thenReturn(factByTitleOpt);
        Mockito.when(regionRepository.findByRegionId(factViewModel1.getRegionId())).thenReturn(regionToReturnOpt);

        factService.addOrEditFact(factViewModel1, false);

        Mockito.verify(factRepository).save(argCaptor.capture());

        assertEquals(factViewModel1.getTitle(), argCaptor.getValue().getTitle());
        assertEquals(factViewModel1.getDescription(), argCaptor.getValue().getDescription());
        assertEquals(factViewModel1.getImageUrl(), argCaptor.getValue().getImageUrl());
        assertEquals(factViewModel1.getType(), argCaptor.getValue().getType());
        assertEquals(factViewModel1.getRegionId(), argCaptor.getValue().getRegion().getRegionId());
        assertEquals(factViewModel1.getReadMore(), argCaptor.getValue().getReadMore());

        assertEquals(regionToReturn.getName(), argCaptor.getValue().getRegion().getName());
        assertEquals(regionToReturn.getArea(), argCaptor.getValue().getRegion().getArea());
        assertEquals(regionToReturn.getImageUrl(), argCaptor.getValue().getRegion().getImageUrl());
        assertEquals(regionToReturn.getPopulation(), argCaptor.getValue().getRegion().getPopulation());
    }

    @Test
    public void addOrEditFact_whenFactToEditIsNotFound_shouldThrowException(){
        FactFormViewModel factViewModel1 = new FactFormViewModel("Fact1", "oldFact1", Type.NATURE, "BG-16", "This is my first add fact test", "myFirstFactTestUrl", "Please go to Google to read more facts");
        Optional<Fact> factOpt = Optional.empty();

        Mockito.when(factRepository.findByTitle(factViewModel1.getOldTitle())).thenReturn(factOpt);

        assertThrows(FactNotFoundException.class, () -> {
            factService.addOrEditFact(factViewModel1, true);
        });
    }

    @Test
    public void addOrEditFact_whenRegionForFactIsNotFound_shouldThrowException(){
        FactFormViewModel factViewModel1 = new FactFormViewModel("Fact1", "oldFact1", Type.NATURE, "BG-16", "This is my first add fact test", "myFirstFactTestUrl", "Please go to Google to read more facts");

        Optional<Region> regionOpt = Optional.empty();

        Mockito.when(regionRepository.findByRegionId(factViewModel1.getRegionId())).thenReturn(regionOpt);

        assertThrows(RegionNotFoundException.class, () -> {
            factService.addOrEditFact(factViewModel1, false);
        });
    }

    @Test
    public void seedFacts_whenCorrectFact_shouldSaveAndFlush() throws IOException {
        RegionServiceModel regionServiceModel = new RegionServiceModel("BG-01", "Blagoevgrad", 32323, 2449.0, "BlagoevgradUrl", null, null);
        Region region = new Region("BG-01", "Blagoevgrad", 32323, 2449.0, "BlagoevgradUrl", null, null);
        Optional regionOpt = Optional.of(region);
        Optional factOpt = Optional.empty();
        String regionId = regionServiceModel.getRegionId();
        FactServiceModel serviceModel = new FactServiceModel("Fact1", "Description1", Type.NATURE, regionServiceModel, "files/img/facts/asen_yordanov.png", "readMoreUrl");

        FactServiceModel[] factServiceModels = {serviceModel};

        Mockito.when(validationService.isValid(serviceModel)).thenReturn(true);
        Mockito.when(regionRepository.findByRegionId(regionId)).thenReturn(regionOpt);
        Mockito.when(factRepository.findByTitle(serviceModel.getTitle())).thenReturn(factOpt);

        factService.seedFacts(factServiceModels);

        Mockito.verify(factRepository).saveAndFlush(argCaptor.capture());

        assertEquals(serviceModel.getTitle(), argCaptor.getValue().getTitle());
        assertEquals(serviceModel.getDescription(), argCaptor.getValue().getDescription());
        assertEquals(serviceModel.getType(), argCaptor.getValue().getType());
        assertEquals(serviceModel.getRegion().getRegionId(), argCaptor.getValue().getRegion().getRegionId());
        assertEquals(serviceModel.getReadMore(), argCaptor.getValue().getReadMore());

        assertEquals(region.getName(), argCaptor.getValue().getRegion().getName());
        assertEquals(region.getArea(), argCaptor.getValue().getRegion().getArea());
        assertEquals(region.getImageUrl(), argCaptor.getValue().getRegion().getImageUrl());
        assertEquals(region.getPopulation(), argCaptor.getValue().getRegion().getPopulation());


    }

}
