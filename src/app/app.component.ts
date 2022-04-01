import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { ROUTE_TO_NAV_ID } from '../constants';
import { AppService } from './app.service';
import { HttpClient } from '@angular/common/http';
import { finalize } from 'rxjs';
import * as $ from 'jquery';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'discover-bulgaria';

  constructor(
    private app: AppService,
    private router: Router,
    private http: HttpClient,
  ) {
    this.app.authenticate();
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        const endEvent = event as NavigationEnd;
        $('li.nav-item.active').removeClass('active');
        $(`#${ROUTE_TO_NAV_ID[endEvent.url]}`).addClass('active');
      }
    });
  }

  public isAuthenticated(): boolean {
    return this.app.authenticated;
  }

  logout() {
    this.http
      .post('logout', {})
      .pipe(
        finalize(() => {
          this.app.authenticated = false;
          this.router.navigateByUrl('/login');
        }),
      )
      .subscribe();
  }

  public ngOnInit(): void {}
}
