import {AfterViewInit, Component, EventEmitter, OnInit} from '@angular/core';
import { Region } from "../interfaces/Region";
import { RegionService } from "../region.service";
import * as $ from 'jquery';
import { Fact } from "../interfaces/Fact";
import { NotifyService } from "../notify.service";
import { CurrentRegion } from "../interfaces/CurrentRegion";
import { CARD_CAT_TYPE_ID_TO_ENUM, Category, Type } from "../../constants";

@Component({
  selector: 'app-svg-map',
  templateUrl: './svg-map.component.html',
  styleUrls: ['./svg-map.component.css']
})
export class SvgMapComponent implements OnInit, AfterViewInit {

  regions: Region[] = [];

  facts: Fact[] = [];

  constructor(
    private regionService: RegionService,
    private notifyService: NotifyService
  ) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.getRegions();
  }

  getRegions(): void {
    this.regionService.getRegions()
      .subscribe(regions => this.regions = regions);
  }

  onClick(event: MouseEvent): void {
    const target = event.target as HTMLElement;
    const jTarget = $(target);
    if (target.id.includes('BG') && !jTarget.hasClass('active')) {
      $('path.active').removeClass('active');
      jTarget.addClass('active');
      this.setNewRegion(target);
    }
  }

  setNewRegion(target: HTMLElement): void {
    const currentTypeId = $('a.type.nav-link.active').attr('id')!;
    const currentCategoryId = $('a.cat.nav-link.active').attr('id')!;
    const currentType: Type = CARD_CAT_TYPE_ID_TO_ENUM[currentTypeId] as Type;
    const currentCategory: Category = CARD_CAT_TYPE_ID_TO_ENUM[currentCategoryId] as Category;
    const newRegion: CurrentRegion = {
      regionId: target.id,
      currentTypeId,
      currentCategoryId,
      type: currentType,
      category: currentCategory,
    };
    this.notifyService.changeRegion(newRegion);
    this.notifyService.setLoading();
  }
}
