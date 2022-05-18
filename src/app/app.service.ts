import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import * as $ from 'jquery';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  authenticated = false;

  constructor(private http: HttpClient, private router: Router) {}

  authenticate(
    credentials?: { username: string; password: string },
    callback?: () => any,
  ) {
    const headers = new HttpHeaders(
      credentials
        ? {
            authorization:
              'Basic ' +
              btoa(credentials.username + ':' + credentials.password),
          }
        : {},
    );

    this.http.get('user', { headers: headers }).subscribe((response: any) => {
      this.authenticated = !!response['name'];
      return callback && callback();
    });
  }

  public toggleMainBackground(): void {
    const mainEl = $('.main');
    if (
      this.router.url.endsWith('login') ||
      this.router.url.endsWith('register')
    ) {
      if (mainEl.hasClass('main-raised')) {
        mainEl.removeClass('main-raised');
        mainEl.css({ background: 'rgba(0,0,0,0)' });
      }
    } else if (!mainEl.hasClass('main-raised')) {
      mainEl.addClass('main-raised');
      mainEl.css({ background: '' });
    }
  }
}
