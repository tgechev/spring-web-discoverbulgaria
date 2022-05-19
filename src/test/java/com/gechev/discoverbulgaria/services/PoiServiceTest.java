package com.gechev.discoverbulgaria.services;

import com.gechev.discoverbulgaria.base.TestBase;
import com.gechev.discoverbulgaria.data.models.*;
import com.gechev.discoverbulgaria.data.repositories.PoiRepository;
import com.gechev.discoverbulgaria.exceptions.PoiNotFoundException;
import com.gechev.discoverbulgaria.exceptions.RegionNotFoundException;
import com.gechev.discoverbulgaria.services.models.PoiServiceModel;
import com.gechev.discoverbulgaria.services.models.RegionServiceModel;
import com.gechev.discoverbulgaria.web.models.PoiFormViewModel;
import com.gechev.discoverbulgaria.web.models.PoiViewModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PoiServiceTest extends TestBase {

    @MockBean
    public ValidationService validationService;

    @MockBean
    PoiRepository poiRepository;

    @MockBean
    ApplicationEventPublisher eventPublisher;

    @Autowired
    PoiService poiService;

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
    void findAll_whenThreeUnorderedPoi_shouldReturnServiceModelsSorted() {

        List<Poi> poi = new ArrayList<>();

        Poi poi1 = new Poi();
        poi1.setTitle("Евксиноград");

        Poi poi2 = new Poi();
        poi2.setTitle("Яйлата");

        Poi poi3 = new Poi();
        poi3.setTitle("Кале");

        poi.add(poi2);
        poi.add(poi1);
        poi.add(poi3);



        Mockito.when(poiRepository.findAll()).thenReturn(poi);

        List<PoiServiceModel> actualPoi = poiService.findAll();

        assertEquals(poi.size(), actualPoi.size(), "Sizes do not match");
        assertEquals(poi.get(0).getTitle(), actualPoi.get(2).getTitle());
        assertEquals(poi.get(1).getTitle(), actualPoi.get(0).getTitle());
        assertEquals(poi.get(2).getTitle(), actualPoi.get(1).getTitle());
    }

    @Test
    void getPoiViewModels_whenThreeUnorderedPoi_shouldReturnViewModelsSorted() {

        List<Poi> poi = new ArrayList<>();

        Poi poi1 = new Poi();
        poi1.setTitle("Евксиноград");
        poi1.setRegion(new Region("BG-01", "Blagoevgrad", 32323, 2449.0, "BlagoevgradUrl", null, null));
        poi1.setCoordinates(new Coordinates(42.8034801, 25.3491946));

        Poi poi2 = new Poi();
        poi2.setTitle("Яйлата");
        poi2.setRegion(new Region("BG-02", "Burgas", 32323, 2449.0, "BurgasUrl", null, null));
        poi2.setCoordinates(new Coordinates(43.4352535, 28.5419001));

        Poi poi3 = new Poi();
        poi3.setTitle("Кале");
        poi3.setRegion(new Region("BG-03", "Varna", 32323, 2449.0, "VarnaUrl", null, null));
        poi3.setCoordinates(new Coordinates(43.3590825, 26.1452022));

        poi.add(poi2);
        poi.add(poi1);
        poi.add(poi3);



        Mockito.when(poiRepository.findAll()).thenReturn(poi);

        List<PoiViewModel> actualPoi = poiService.getPoiViewModels();

        assertEquals(poi.size(), actualPoi.size(), "Sizes do not match");
        assertEquals(poi.get(0).getTitle(), actualPoi.get(2).getTitle());
        assertEquals(poi.get(0).getRegion().getRegionId(), actualPoi.get(2).getRegionId());
        assertEquals(poi.get(0).getCoordinates().getLatitude(), actualPoi.get(2).getLatitude());
        assertEquals(poi.get(0).getCoordinates().getLongitude(), actualPoi.get(2).getLongitude());
        assertEquals(poi.get(1).getTitle(), actualPoi.get(0).getTitle());
        assertEquals(poi.get(2).getTitle(), actualPoi.get(1).getTitle());
    }

    @Test
    public void addOrEditPoi_whenPoiToEditIsNotFound_shouldThrowException(){
        PoiFormViewModel viewModel = new PoiFormViewModel("Poi1", "oldPoi1", Type.NATURE, "BG-16", "poiAddress", 43.3590825, 26.1452022, "poiDescription", "poiImageUrl", "poiReadMore");
        Optional<Poi> factOpt = Optional.empty();

        Mockito.when(poiRepository.findByTitle(viewModel.getOldTitle())).thenReturn(factOpt);

        assertThrows(PoiNotFoundException.class, () -> {
            poiService.addOrEditPoi(viewModel, true);
        });
    }

    @Test
    public void addOrEditPoi_whenRegionForPoiIsNotFound_shouldThrowException(){
        PoiFormViewModel viewModel = new PoiFormViewModel("Poi1", "oldPoi1", Type.NATURE, "BG-16", "poiAddress", 43.3590825, 26.1452022, "poiDescription", "poiImageUrl", "poiReadMore");

        Optional<Region> regionOpt = Optional.empty();

        Mockito.when(regionRepository.findByRegionId(viewModel.getRegionId())).thenReturn(regionOpt);

        assertThrows(RegionNotFoundException.class, () -> {
            poiService.addOrEditPoi(viewModel, false);
        });
    }

    @Test
    public void seedPoi_whenRegionForPoiIsNotFound_shouldPrintMessage(){
        PoiServiceModel serviceModel = new PoiServiceModel("Evksinograd", "", "", Type.NATURE, null, new RegionServiceModel("BG-1", "Blagoevgrad", 32323, 2449.0, "BlagoevgradUrl", null, null), "", "");
        Mockito.when(validationService.isValid(serviceModel)).thenReturn(true);
        Optional<Region> regionOpt = Optional.empty();

        PoiServiceModel[] models = {serviceModel};

        String regionId = serviceModel.getRegion().getRegionId();
        Mockito.when(regionRepository.findByRegionId(regionId)).thenReturn(regionOpt);

        poiService.seedPoi(models);

        String expected = "Poi not added, reason: could not find region with regionId: BG-1\n".replaceAll("\\n", System.getProperty("line.separator"));
        assertEquals(expected, outContent.toString());
    }

}
