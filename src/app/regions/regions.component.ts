import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { RegionService } from '../region.service';
import { Region } from '../interfaces/Region';

@Component({
  selector: 'app-regions',
  templateUrl: './regions.component.html',
  styleUrls: ['./regions.component.css'],
})
export class RegionsComponent implements OnInit {
  constructor(private app: AppService, private regionService: RegionService) {}

  regions: Region[] = [];

  ngOnInit(): void {
    this.app.toggleMainBackground();
  }

  ngAfterViewInit(): void {
    this.getRegions();
  }

  getRegions(): void {
    this.regionService
      .getRegions()
      .subscribe(regions => (this.regions = regions));
  }
}
