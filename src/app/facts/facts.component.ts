import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { RegionService } from '../region.service';
import { Region } from '../interfaces/Region';

@Component({
  selector: 'app-facts',
  templateUrl: './facts.component.html',
  styleUrls: ['./facts.component.css'],
})
export class FactsComponent implements OnInit {
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
