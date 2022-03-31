import {Component, ElementRef, OnInit, ViewChild,} from '@angular/core';
import {Router} from "@angular/router";
import {animate, state, style, transition, trigger,} from '@angular/animations';
import {PoiService} from "../poi.service";
import {FactService} from "../fact.service";
import {CardView} from "../interfaces/CardView";
import {NotifyService} from "../notify.service";
import {CurrentRegion} from "../interfaces/CurrentRegion";
import {AnimState, Category} from "../../constants";
import {Poi} from "../interfaces/Poi";
import * as $ from 'jquery';
import {YouTubePlayer} from "@angular/youtube-player";

const commonAnimStyleProps = {
  position: 'fixed',
  top: '50%',
  left: '50%',
  zIndex: 1020,
  width: '50vw',
  height: '85vh',
};

@Component({
  selector: 'app-card-deck',
  templateUrl: './card-deck.component.html',
  styleUrls: ['./card-deck.component.css'],
  animations: [
    trigger('popup', [
      state(AnimState.SHOW, style({
        ...commonAnimStyleProps,
        opacity: 1,
        transform: 'translate(-50%, -45%)',
      })),
      state(AnimState.HIDE, style({
        ...commonAnimStyleProps,
        opacity: 0,
        transform: 'translate(-50%, -55%)',
      })),
      state(AnimState.EXIT, style({
        ...commonAnimStyleProps,
        opacity: 0,
        transform: 'translate(-50%, -45%) scale(0.9)',
      })),
      transition('hide => show', [
        animate('0.3s ease-in')
      ]),
      transition('show => exit', [
        animate('0.2s ease-out')
      ]),
    ]),
  ]
})
export class CardDeckComponent implements OnInit {

  public all: boolean = false;

  public cards: CardView[] = [];
  public loading: boolean = false;
  public popupCard?: CardView;
  public showPopupVideo = true;
  public popupState: AnimState = AnimState.HIDE;

  @ViewChild('yt_player')
  youtubePlayer?: YouTubePlayer;

  constructor(
    private rootElement: ElementRef,
    private router: Router,
    private poiService: PoiService,
    private factService: FactService,
    private notifyService: NotifyService
  ) {  }

  ngOnInit(): void {
    if (this.router.url.endsWith('all')) {
      this.all = true;
      if (this.router.url.includes('poi')) {
        this.getAllPoi();
      } else {
        this.getAllFacts();
      }
    } else {
      this.notifyService.isLoading.subscribe(loading => this.loading = loading);
      this.notifyService.currentRegion.subscribe(region => this.getCardsForRegion(region));
    }
  }

  onPopupDone() {
    if (this.popupState === AnimState.EXIT) {
      this.popupState = AnimState.HIDE;
      $('#popup-card').css('display', 'none');
    } else {
      this.showPopupVideo = !this.showPopupVideo;
    }
  }

  private static setActiveRegionCategoryType(currentRegion: string, currentType: string, currentCategory: string): void {
    if (currentRegion) {
      $(`#${currentRegion}`).addClass('active');

      $('a.type.nav-link.active').removeClass('active');
      $(`#${currentType}`).addClass('active');

      $('a.cat.nav-link.active').removeClass('active');
      $(`#${currentCategory}`).addClass('active');
    }
  }

  private getCardsForRegion(currentRegion: CurrentRegion): void {
    CardDeckComponent.setActiveRegionCategoryType(currentRegion.regionId, currentRegion.currentTypeId, currentRegion.currentCategoryId);
    if (currentRegion.category !== Category.ALL) {
      if (currentRegion.category === Category.FACT) {
        this.factService.getFactsByRegion(currentRegion.regionId, currentRegion.type)
          .subscribe(facts => {
            this.cards = facts;
            this.loading = false;
          });
      } else {
        this.poiService.getPoiByRegion(currentRegion.regionId, currentRegion.type)
          .subscribe(poi => {
            this.cards = poi
            this.loading = false;
          });
      }
    } else {
      this.factService.getFactsByRegion(currentRegion.regionId, currentRegion.type).subscribe(facts => {
        this.poiService.getPoiByRegion(currentRegion.regionId, currentRegion.type).subscribe(poi => {
          this.cards = [...facts, ...poi];
          this.loading = false;
        });
      });
    }
  }

  private getAllPoi(): void {
    this.poiService.getAllPoi().subscribe(poi => this.cards = poi);
  }

  private getAllFacts(): void {
    this.factService.getAllFacts().subscribe(facts => this.cards = facts);
  }

  public showOnMap(event: MouseEvent, showOnMapObj?: JQuery): void {
    event.preventDefault();
    let poiIdStr: string;
    if (showOnMapObj) {
      poiIdStr = showOnMapObj.attr('show-card')!;
    } else {
      poiIdStr = $(event.target as HTMLElement).closest("*[show-card]").attr('show-card')!;
    }
    const poiId: number = Number.parseInt(poiIdStr);
    const thePoi: Poi = this.cards.find(card => card.id === poiId)!;
    this.notifyService.showPoiOnMap(thePoi);
  }

  public onCardClick(event: MouseEvent): void {
    const target = event.target as HTMLElement;
    const currentTarget = event.currentTarget as HTMLElement;
    const openLink = $(target).closest("*[open-link]");
    const showOnMap = $(target).closest("*[show-card]");
    if (!openLink.data() && !showOnMap.data()) {
      event.preventDefault();
      $('#popup-card').css('display', 'flex');
      const popupCardId = Number.parseInt(currentTarget.id);
      this.popupCard = this.cards.find(card => card.id === popupCardId);
      this.popupState = AnimState.SHOW;
      $('#popup-background').addClass('active');
    } else if (showOnMap.data()) {
      this.showOnMap(event, showOnMap);
    }
  }

  public exitPopup(): void {
    $('#popup-background').removeClass('active');
    this.popupState = AnimState.EXIT;
    this.youtubePlayer?.pauseVideo();
  }
}
