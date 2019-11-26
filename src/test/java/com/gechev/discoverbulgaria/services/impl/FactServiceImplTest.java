package com.gechev.discoverbulgaria.services.impl;

import com.cloudinary.Cloudinary;
import com.gechev.discoverbulgaria.data.models.Fact;
import com.gechev.discoverbulgaria.data.repositories.FactRepository;
import com.gechev.discoverbulgaria.data.repositories.RegionRepository;
import com.gechev.discoverbulgaria.services.FactService;
import com.gechev.discoverbulgaria.services.ValidationService;
import com.gechev.discoverbulgaria.services.models.FactServiceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FactServiceImplTest {

    private FactRepository factRepository;
    private RegionRepository regionRepository;
    private FactService factService;
    private ValidationService validationService;
    private List<Fact> facts;

    @BeforeEach
    void setUp(){
        facts = new ArrayList<>();
        factRepository = Mockito.mock(FactRepository.class);
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
}