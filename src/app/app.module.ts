import { Injectable, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { YouTubePlayerModule } from '@angular/youtube-player';
import {
  HTTP_INTERCEPTORS,
  HttpClientModule,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { FileUploadModule } from 'ng2-file-upload';
import { CloudinaryModule } from '@cloudinary/angular-5.x';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { SvgMapComponent } from './svg-map/svg-map.component';
import { TooltipDirective } from './tooltip.directive';
import { CardDeckComponent } from './card-deck/card-deck.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { AppService } from './app.service';
import { RegisterComponent } from './register/register.component';
import { ActiveLabelDirective } from './active-label.directive';
import { RegionsComponent } from './regions/regions.component';
import { UsersComponent } from './users/users.component';
import { FactsComponent } from './facts/facts.component';
import { PoiComponent } from './poi/poi.component';
import * as cloudinary from 'cloudinary-core';
import { cloudinaryConfig } from '../constants';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest'),
    });
    return next.handle(xhr);
  }
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SvgMapComponent,
    TooltipDirective,
    CardDeckComponent,
    LoginComponent,
    RegisterComponent,
    ActiveLabelDirective,
    RegionsComponent,
    UsersComponent,
    FactsComponent,
    PoiComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    YouTubePlayerModule,
    ReactiveFormsModule,
    CloudinaryModule.forRoot(cloudinary, cloudinaryConfig),
    FileUploadModule,
  ],
  providers: [
    AppService,
    { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
