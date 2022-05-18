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

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'poi/all', component: CardDeckComponent },
  { path: 'facts/all', component: CardDeckComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'regions/edit', component: RegionsComponent },
  { path: 'facts/edit', component: FactsComponent },
  { path: 'facts/add', component: FactsComponent },
  { path: 'poi/edit', component: PoiComponent },
  { path: 'poi/add', component: PoiComponent },
  { path: 'users/edit', component: UsersComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
