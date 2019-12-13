package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.base.TestBase;
import com.gechev.discoverbulgaria.constants.Constants;
import com.gechev.discoverbulgaria.data.models.Coordinates;
import com.gechev.discoverbulgaria.data.models.Poi;
import com.gechev.discoverbulgaria.data.models.Region;
import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import com.gechev.discoverbulgaria.web.models.EditRegionModel;
import com.gechev.discoverbulgaria.web.models.PoiFormViewModel;
import com.gechev.discoverbulgaria.web.models.RegionViewModel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RegionServiceTest extends TestBase {

    @MockBean
    ValidationService validationService;

    @Autowired
    RegionService regionService;

    @Captor
    ArgumentCaptor<Region> argCaptor;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Override
    protected void beforeEach() {
        System.setOut(new PrintStream(outContent));
    }

    @Override
    protected void afterEach() {
        System.setOut(originalOut);
    }

    @Test
    void findAll_whenThreeUnorderedRegions_shouldReturnServiceModelsSorted() {

        List<Region> regions = new ArrayList<>();

        Region region1 = new Region();
        region1.setName("Blagoevgrad");

        Region region2 = new Region();
        region2.setName("Silistra");

        Region region3 = new Region();
        region3.setName("Kyustendil");

        regions.add(region2);
        regions.add(region1);
        regions.add(region3);



        Mockito.when(regionRepository.findAll()).thenReturn(regions);

        List<RegionServiceModel> actualRegions = regionService.findAll();

        assertEquals(regions.size(), actualRegions.size(), "Sizes do not match");
        assertEquals(regions.get(0).getName(), actualRegions.get(2).getName());
        assertEquals(regions.get(1).getName(), actualRegions.get(0).getName());
        assertEquals(regions.get(2).getName(), actualRegions.get(1).getName());
    }

    @Test
    void seedRegions_whenRegionAlreadyExists_shouldPrintMessage() throws IOException {
        RegionServiceModel serviceModel = new RegionServiceModel("BG-01", "Blagoevgrad", 32323, 2449.0, "BlagoevgradUrl", null, null);
        Region region = new Region("BG-01", "Blagoevgrad", 32323, 2449.0, "BlagoevgradUrl", null, null);
        Optional<Region> regionOpt = Optional.of(region);
        Mockito.when(validationService.isValid(serviceModel)).thenReturn(true);
        Mockito.when(regionRepository.findByRegionId(serviceModel.getRegionId())).thenReturn(regionOpt);
        RegionServiceModel[] models = {serviceModel};

        regionService.seedRegions(models);

        String expected = "Region Blagoevgrad already exists.\n".replaceAll("\\n", System.getProperty("line.separator"));
        assertEquals(expected, outContent.toString());
    }

    @Test
    void editRegion_whenIncorrectModelIsGiven_shouldReturnFalse(){
        EditRegionModel editModel = new EditRegionModel("Varna", "BG-3", 23232.0, 213131, "BG-03", "VarnaUrl");

        boolean result = regionService.editRegion(editModel);
        assertFalse(result);
    }

    @Test
    void getRegionViewModels_whenThreeUnorderedRegions_shouldReturnViewModelsSorted() {

        List<Region> regions = new ArrayList<>();

        Region region1 = new Region("BG-01", "Blagoevgrad", 323232, 232323.0, "BlagoevgradUrl", null, null);

        Region region2 = new Region("BG-03", "Varna", 22222, 121212.0, "VarnaUrl", null, null);

        Region region3 = new Region("BG-02", "Burgas", 444444, 141414.0, "BurgasUrl", null, null);

        regions.add(region2);
        regions.add(region1);
        regions.add(region3);



        Mockito.when(regionRepository.findAll()).thenReturn(regions);

        List<RegionViewModel> actualRegions = regionService.getRegionViewModels();

        assertEquals(regions.size(), actualRegions.size(), "Sizes do not match");
        assertEquals(regions.get(0).getName(), actualRegions.get(2).getName());
        assertEquals(regions.get(0).getRegionId(), actualRegions.get(2).getRegionId());
        assertEquals(regions.get(0).getArea(), actualRegions.get(2).getArea());
        assertEquals(regions.get(0).getPopulation(), actualRegions.get(2).getPopulation());
        assertEquals(Constants.CLOUDINARY_BASE_URL + regions.get(0).getImageUrl(), actualRegions.get(2).getImageUrl());
        assertEquals(regions.get(1).getName(), actualRegions.get(0).getName());
        assertEquals(regions.get(2).getName(), actualRegions.get(1).getName());
    }
}
