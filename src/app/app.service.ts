import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import * as $ from 'jquery';
import { Router } from '@angular/router';
import { UserRole } from '../constants';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  authenticated = false;
  loggedInUser: string = '';

  private userRole: UserRole = UserRole.User;

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
      this.loggedInUser = response.name;
      response.authorities.forEach((auth: any) => {
        switch (auth.authority) {
          case UserRole.Root:
            this.userRole = UserRole.Root;
            break;
          case UserRole.Admin:
            if (this.userRole !== UserRole.Root) {
              this.userRole = UserRole.Admin;
            }
            break;
        }
      });
      return callback && callback();
    });
  }

  public isAdmin(): boolean {
    return this.userRole === UserRole.Root || this.userRole === UserRole.Admin;
  }

  public isRoot(): boolean {
    return this.userRole === UserRole.Root;
  }

  public resetLoggedInUserRole(): void {
    this.userRole = UserRole.User;
  }

  public getLoggedInUser(): string {
    return this.loggedInUser;
  }

  public toggleMainBackground(): void {
    const mainEl = $('.main');
    if (!this.router.url.endsWith('home') && !this.router.url.endsWith('all')) {
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
