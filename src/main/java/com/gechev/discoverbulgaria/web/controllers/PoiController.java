package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.data.models.Type;
import com.gechev.discoverbulgaria.services.PoiService;
import com.gechev.discoverbulgaria.web.models.BaseViewModel;
import com.gechev.discoverbulgaria.web.models.DeleteModel;
import com.gechev.discoverbulgaria.web.models.FactViewModel;
import com.gechev.discoverbulgaria.web.models.PoiViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/poi")
public class PoiController {

  private final PoiService poiService;

  public PoiController(PoiService poiService) {
    this.poiService = poiService;
  }

  @PostMapping("/add")
  public ResponseEntity<PoiViewModel> addPoi(@RequestBody PoiViewModel poiViewModel){
    return ResponseEntity.ok(this.poiService.addOrEditPoi(poiViewModel, false));
  }

  @PostMapping("/edit")
  public ResponseEntity<PoiViewModel> editPoi(@RequestBody PoiViewModel poiViewModel){
    return ResponseEntity.ok(this.poiService.addOrEditPoi(poiViewModel, true));
  }

  @PostMapping("/delete")
  public ResponseEntity<DeleteModel> deletePoi(@RequestBody BaseViewModel poiDeleteModel){
    return ResponseEntity.ok(this.poiService.deletePoi(poiDeleteModel));
  }

  @GetMapping("/all")
  public List<PoiViewModel> getAllPoi() {
    return this.poiService.getPoiViewModels();
  }

  @GetMapping(value = "/{regionId}")
  public List<PoiViewModel> getPoiForRegion(@PathVariable String regionId, @RequestParam Optional<String> type) {
    if (type.isPresent()) {
      return this.poiService.getPoiByRegionIdAndType(regionId, Type.valueOf(type.get()));
    } else {
      return this.poiService.getPoiByRegionId(regionId);
    }
  }
}
