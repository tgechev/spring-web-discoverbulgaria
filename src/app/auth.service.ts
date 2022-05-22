import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UserRole } from '../constants';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService implements CanActivate {
  authenticated = false;
  loggedInUser: string = '';

  userRole: UserRole = UserRole.User;

  constructor(private http: HttpClient, public router: Router) {}

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

  canActivate(): boolean {
    if (!this.authenticated) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
