import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { RegionService } from '../region.service';
import { Region } from '../interfaces/Region';
import * as $ from 'jquery';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ResponseData } from '../dto/response-data';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

@Component({
  selector: 'app-regions',
  templateUrl: './regions.component.html',
  styleUrls: ['./regions.component.css'],
})
export class RegionsComponent implements OnInit, AfterViewInit {
  regions: Region[] = [];
  selectedRegion: Region = {
    id: '',
    regionId: '',
    area: 0,
    name: '',
    population: 0,
    imageUrl: '',
  };
  constructor(
    private app: AppService,
    private regionService: RegionService,
    private http: HttpClient,
  ) {}

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

  onSelectRegion(): void {
    $('input').each(function () {
      if ($(this).val()) {
        $(`label[for=${this.id}]`).addClass('active');
      }
    });
  }

  assignSelectedRegion(event: Region): void {
    Object.assign(this.selectedRegion, event);
  }

  onSubmit(): void {
    this.http
      .post<ResponseData>('api/regions/edit', this.selectedRegion, httpOptions)
      .subscribe((data: ResponseData) => {
        if (data.responseCode == '200') {
          console.log(`success edit region`);
        }
      }),
      (error: string) => {
        console.log('An Error Occurred ' + error);
      };
  }
}
