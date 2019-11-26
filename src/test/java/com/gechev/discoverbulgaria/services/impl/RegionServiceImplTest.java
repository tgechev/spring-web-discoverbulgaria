package com.gechev.discoverbulgaria.services.impl;

import com.cloudinary.Cloudinary;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.data.repositories.RegionRepository;
import com.gechev.discoverbulgaria.services.RegionService;
import com.gechev.discoverbulgaria.services.ValidationService;
import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import com.gechev.discoverbulgaria.web.models.EditRegionModel;
import com.gechev.discoverbulgaria.web.models.RegionViewModel;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RegionServiceImplTest {

    private RegionRepository regionRepository;
    private RegionService regionService;
    private ValidationService validationService;

    private List<Region> regions;

    @BeforeEach
    void setUp() {
        regions = new ArrayList<>();
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

        Region blagoevgrad = new Region();
        blagoevgrad.setName("Благоевград");
        blagoevgrad.setRegionId("BG-01");

        Region burgas = new Region();
        burgas.setName("Бургас");
        burgas.setRegionId("BG-02");

        Region varna = new Region();
        varna.setName("Варна");
        varna.setRegionId("BG-03");
        regions.add(varna);
        regions.add(blagoevgrad);
        regions.add(burgas);

        Mockito.when(regionRepository.findAll()).thenReturn(regions);

        regionService = new RegionServiceImpl(regionRepository, new ModelMapper(), validationService, new Cloudinary());
    }

    @Test
    void findAll_whenThreeUnorderedRegions_shouldReturnThemSorted() {

        List<RegionServiceModel> actualRegions = regionService.findAll();

        assertEquals(regions.size(), actualRegions.size(), "Sizes do not match");
        assertEquals(regions.get(0).getName(), actualRegions.get(2).getName());
        assertEquals(regions.get(1).getName(), actualRegions.get(0).getName());
        assertEquals(regions.get(2).getName(), actualRegions.get(1).getName());
    }

    @Test
    void getRegionViewModels_whenThreeUnorderedRegions_shouldReturnSortedViewModels() {
        List<RegionViewModel> viewModels = regionService.getRegionViewModels();

        assertEquals(regions.size(), viewModels.size(), "Sizes do not match");
        assertEquals(regions.get(0).getName(), viewModels.get(2).getName());
        assertEquals(regions.get(1).getName(), viewModels.get(0).getName());
        assertEquals(regions.get(2).getName(), viewModels.get(1).getName());
    }

    @Test
    void editRegion_whenRegionExists_shouldReturnTrue() {
        Region blagoevgrad = new Region();
        blagoevgrad.setName("Благоевград");
        blagoevgrad.setRegionId("BG-01");
        blagoevgrad.setImageUrl("OldBlagoevGradUrl");
        blagoevgrad.setPopulation(310321);
        blagoevgrad.setArea(6449.47);
        Optional<Region> regionOptional = Optional.of(blagoevgrad);

        EditRegionModel theModel = new EditRegionModel("Благоевград", "BG-1", 6550.0, 323232, "BG-01", "blagoevGrad_url");

        Mockito.when(regionRepository.findByRegionId(theModel.getTheId())).thenReturn(regionOptional);

        boolean result = regionService.editRegion(theModel);

        assertTrue(result);
    }

    @Test
    void editRegion_whenRegionDoesNotExist_shouldReturnFalse() {

        Optional<Region> regionOptional = Optional.empty();

        EditRegionModel theModel = new EditRegionModel("Благоевград", "BG-1", 6550.0, 323232, "BG-01", "blagoevGrad_url");

        Mockito.when(regionRepository.findByRegionId(theModel.getTheId())).thenReturn(regionOptional);

        boolean result = regionService.editRegion(theModel);

        assertFalse(result);
    }

}