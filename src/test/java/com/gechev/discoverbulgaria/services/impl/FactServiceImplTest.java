package com.gechev.discoverbulgaria.services.impl;

import com.cloudinary.Cloudinary;
import com.gechev.discoverbulgaria.data.models.Fact;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.models.Type;
import com.gechev.discoverbulgaria.data.repositories.FactRepository;
import com.gechev.discoverbulgaria.data.repositories.RegionRepository;
import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.ValidationService;
import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import com.gechev.discoverbulgaria.web.models.FactFormViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FactServiceImplTest {

    @Mock
    private FactRepository factRepository;

    private RegionRepository regionRepository;
    private FactService factService;
    private ValidationService validationService;
    private List<Fact> facts;

    @Captor
    ArgumentCaptor<Fact> argCaptor;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        facts = new ArrayList<>();
        //factRepository = Mockito.mock(FactRepository.class);
        regionRepository = Mockito.mock(RegionRepository.class);
        validationService = new ValidationService() {
            @Override
            public <E> boolean isValid(E entity) {
                return true;
            }

            @Override
            public <E> Set<ConstraintViolation<E>> violations(E entity) {
                return null;
            }
        };

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

        ApplicationEventPublisher publisher = o -> System.out.println("Event published");

        factService = new FactServiceImpl(validationService, new ModelMapper(), factRepository, regionRepository, new Cloudinary(), publisher);
    }

    @Test
    void findAll_whenThreeUnorderedFacts_shouldReturnThemSorted() {
        List<FactServiceModel> actualFacts = factService.findAll();

        assertEquals(facts.size(), actualFacts.size(), "Sizes do not match");
        assertEquals(facts.get(0).getTitle(), actualFacts.get(2).getTitle());
        assertEquals(facts.get(1).getTitle(), actualFacts.get(0).getTitle());
        assertEquals(facts.get(2).getTitle(), actualFacts.get(1).getTitle());
    }

    @Test
    void addOrEditFact_whenFactFormViewModelIsGiven_shouldMapAndSaveToDbCorrectly(){
        FactFormViewModel factViewModel1 = new FactFormViewModel("Fact1", "oldFact1", Type.NATURE, "BG-16", "This is my first add fact test", "myFirstFactTestUrl", "Please go to Google to read more facts");

        Fact factByTitle = new Fact("Fact1", "old description", Type.HISTORY, "someUrl", "do not read more", null);
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
}