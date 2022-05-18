import { Component, HostListener, OnInit } from '@angular/core';
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
    public app: AppService,
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
          this.app.resetLoggedInUserRole();
          this.router.navigateByUrl('/login');
        }),
      )
      .subscribe();
  }

  public ngOnInit(): void {}

  @HostListener('window:click', ['$event'])
  onClick(event: MouseEvent) {
    if (!this.router.url.endsWith('home')) {
      const target = event.target as HTMLElement;
      const tagName: string = target.tagName.toLowerCase();
      if (tagName !== 'input' && tagName !== 'textarea') {
        this.disableLabelForEmptyInput();
      } else if (tagName === 'input' || tagName === 'textarea') {
        this.disableLabelForEmptyInput();
        $(`label[for=${target.id}]`).addClass('active');
      }
    }
  }

  private disableLabelForEmptyInput(): void {
    const activeLabels = $('.active');
    activeLabels.each(function () {
      const label = $(this);
      if (label.html()) {
        const input = $(`#${label.attr('for')}`);
        if (!input.val()) {
          label.removeClass('active');
        }
      }
    });
  }
}
