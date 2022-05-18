import {
  Component,
  OnInit,
  AfterViewInit,
  ViewChild,
  ElementRef,
} from '@angular/core';
import * as $ from 'jquery';
import { Poi } from '../interfaces/Poi';
import { NotifyService } from '../notify.service';
import { AppService } from '../app.service';

declare var H: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit, AfterViewInit {
  private platform: any;
  private map: any;

  @ViewChild('mapContainer')
  mapContainer?: ElementRef;

  constructor(private notifyService: NotifyService, private app: AppService) {
    // Init platform object
    this.platform = new H.service.Platform({
      apikey: '_NHpWdVPwg24udbhSq1dNlk0CSUZKICUOUeAvQTMSpQ',
    });

    this.notifyService.currentPoi.subscribe(poi => {
      this.mapContainer?.nativeElement.scrollIntoView();
      this.showPoi(poi);
    });
  }

  ngOnInit(): void {
    this.app.toggleMainBackground();
  }

  ngAfterViewInit(): void {
    this.initHereMap();
  }

  setActiveCatOrType(event: MouseEvent): void {
    const target = event.target as HTMLElement;
    const jTarget = $(target);
    if (target.tagName.toLowerCase() === 'a') {
      if (jTarget.hasClass('cat')) {
        $('.cat.active').removeClass('active');
      } else {
        $('.type.active').removeClass('active');
      }
      jTarget.addClass('active');
    }
  }

  public isAuthenticated(): boolean {
    return this.app.authenticated;
  }

  private initHereMap(): void {
    // Obtain the default map types from the platform object
    var maptypes = this.platform.createDefaultLayers();
    // Instantiate (and display) a map object:
    this.map = new H.Map(
      document.getElementById('mapContainer'),
      maptypes.raster.satellite.map,
      {
        zoom: 7.5,
        center: { lat: 42.63458, lng: 25.57753 },
      },
    );
    // Enable the event system on the map instance:
    var mapEvents = new H.mapevents.MapEvents(this.map);
    // Instantiate the default behavior, providing the mapEvents object:
    var behavior = new H.mapevents.Behavior(mapEvents);
    // Create the default UI:
    var ui = H.ui.UI.createDefault(this.map, maptypes);
  }

  showPoi(poi: Poi): void {
    if (poi.latitude) {
      const poiCoords = {
        lat: poi.latitude,
        lng: poi.longitude,
      };
      const marker = new H.map.Marker(poiCoords);
      this.map.addObject(marker);
      this.map.getViewModel().setLookAtData(
        {
          position: poiCoords,
          zoom: 17,
        },
        true,
      );
    }
  }
}
