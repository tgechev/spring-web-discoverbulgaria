import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';

@Component({
  selector: 'app-poi',
  templateUrl: './poi.component.html',
  styleUrls: ['./poi.component.css'],
})
export class PoiComponent implements OnInit {
  constructor(private app: AppService) {}

  ngOnInit(): void {
    this.app.toggleMainBackground();
  }
}
