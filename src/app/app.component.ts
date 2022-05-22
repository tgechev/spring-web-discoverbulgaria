import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { ROUTE_TO_NAV_ID } from '../constants';
import { HttpClient } from '@angular/common/http';
import { finalize } from 'rxjs';
import * as $ from 'jquery';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'discover-bulgaria';

  constructor(
    private router: Router,
    private http: HttpClient,
    public authService: AuthService,
  ) {
    this.authService.authenticate();
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        const endEvent = event as NavigationEnd;
        $('li.nav-item.active').removeClass('active');
        $(`#${ROUTE_TO_NAV_ID[endEvent.url]}`).addClass('active');
      }
    });
  }

  public isAuthenticated(): boolean {
    return this.authService.authenticated;
  }

  logout() {
    this.http
      .post('logout', {})
      .pipe(
        finalize(() => {
          this.authService.authenticated = false;
          this.authService.resetLoggedInUserRole();
          this.router.navigateByUrl('/login');
        }),
      )
      .subscribe();
  }

  public ngOnInit(): void {}
}
