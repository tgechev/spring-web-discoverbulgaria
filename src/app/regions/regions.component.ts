import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';

@Component({
  selector: 'app-regions',
  templateUrl: './regions.component.html',
  styleUrls: ['./regions.component.css'],
})
export class RegionsComponent implements OnInit {
  constructor(private app: AppService) {}

  ngOnInit(): void {
    this.app.toggleMainBackground();
  }
}
