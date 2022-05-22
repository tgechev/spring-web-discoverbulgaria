import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class RoleGuardService implements CanActivate {
  constructor(public auth: AuthService, public router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data['expectedRole'];
    if (!this.auth.authenticated) {
      console.log(`not authenticated`);
      this.router.navigate(['login']);
      return false;
    } else if (this.auth.userRole !== expectedRole) {
      console.log(`role missing go home`);
      this.router.navigate(['home']);
      return false;
    }
    return true;
  }
}
