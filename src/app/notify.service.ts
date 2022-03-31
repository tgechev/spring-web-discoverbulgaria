import { Injectable } from '@angular/core';
import { BehaviorSubject } from "rxjs";
import { CurrentRegion } from "./interfaces/CurrentRegion";
import { Poi } from "./interfaces/Poi";

@Injectable({
  providedIn: 'root'
})
export class NotifyService {

  private regionSource = new BehaviorSubject({} as CurrentRegion);
  private poiSource = new BehaviorSubject({} as Poi);
  private loadingSource = new BehaviorSubject(false);
  currentRegion = this.regionSource.asObservable();
  isLoading = this.loadingSource.asObservable();
  currentPoi = this.poiSource.asObservable();

  constructor() { }

  changeRegion(currentRegion: CurrentRegion): void {
    this.regionSource.next(currentRegion);
  }

  setLoading(): void {
    this.loadingSource.next(true);
  }

  showPoiOnMap(poi: Poi): void {
    this.poiSource.next(poi);
  }
}
