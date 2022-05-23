import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CardDeckComponent } from './card-deck/card-deck.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { RegionsComponent } from './regions/regions.component';
import { FactsComponent } from './facts/facts.component';
import { PoiComponent } from './poi/poi.component';
import { UsersComponent } from './users/users.component';
import { AuthService as AuthGuard } from './auth.service';
import { RoleGuardService as RoleGuard } from './role-guard.service';
import { UserRole } from '../constants';

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full',
    // canActivate: [AuthGuard],
  },
  {
    path: 'poi/all',
    component: CardDeckComponent /*canActivate: [AuthGuard]*/,
  },
  {
    path: 'facts/all',
    component: CardDeckComponent /*canActivate: [AuthGuard]*/,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'regions/edit',
    component: RegionsComponent,
    // canActivate: [RoleGuard],
    // data: {
    //   expectedRole: UserRole.Admin,
    // },
  },
  {
    path: 'facts/edit',
    component: FactsComponent,
    // canActivate: [RoleGuard],
    // data: {
    //   expectedRole: UserRole.Admin,
    // },
  },
  {
    path: 'facts/add',
    component: FactsComponent,
    // canActivate: [RoleGuard],
    // data: {
    //   expectedRole: UserRole.Admin,
    // },
  },
  {
    path: 'poi/edit',
    component: PoiComponent,
    // canActivate: [RoleGuard],
    // data: {
    //   expectedRole: UserRole.Admin,
    // },
  },
  {
    path: 'poi/add',
    component: PoiComponent,
    // canActivate: [RoleGuard],
    // data: {
    //   expectedRole: UserRole.Admin,
    // },
  },
  {
    path: 'users/edit',
    component: UsersComponent,
    // canActivate: [RoleGuard],
    // data: {
    //   expectedRole: UserRole.Root,
    // },
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
