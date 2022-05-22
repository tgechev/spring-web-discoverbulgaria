import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { UserRole } from '../constants';

@Injectable({
  providedIn: 'root',
})
export class RoleGuardService implements CanActivate {
  constructor(public auth: AuthService, public router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data['expectedRole'];
    if (!this.auth.authenticated) {
      this.router.navigate(['login']);
      return false;
    } else if (
      this.auth.userRole !== UserRole.Root &&
      this.auth.userRole !== expectedRole
    ) {
      this.router.navigate(['home']);
      return false;
    }
    return true;
  }
}
