import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';

@Component({
  selector: 'app-facts',
  templateUrl: './facts.component.html',
  styleUrls: ['./facts.component.css'],
})
export class FactsComponent implements OnInit {
  constructor(private app: AppService) {}

  ngOnInit(): void {
    this.app.toggleMainBackground();
  }
}
