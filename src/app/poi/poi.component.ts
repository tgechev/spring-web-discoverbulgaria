import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { RegionService } from '../region.service';
import { Region } from '../interfaces/Region';
import { Poi } from '../interfaces/Poi';
import { PoiService } from '../poi.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-poi',
  templateUrl: './poi.component.html',
  styleUrls: ['./poi.component.css'],
})
export class PoiComponent implements OnInit {
  constructor(
    private app: AppService,
    private regionService: RegionService,
    private poiService: PoiService,
    private router: Router,
  ) {}

  regions: Region[] = [];
  poi: Poi[] = [];

  ngOnInit(): void {
    this.app.toggleMainBackground();
  }

  ngAfterViewInit(): void {
    this.getRegions();
    this.getPoi();
  }

  getRegions(): void {
    this.regionService
      .getRegions()
      .subscribe(regions => (this.regions = regions));
  }

  getPoi(): void {
    this.poiService.getAllPoi().subscribe(poi => (this.poi = poi));
  }

  isEdit(): boolean {
    return this.router.url.endsWith('edit');
  }
}
