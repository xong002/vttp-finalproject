import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './component/home/home.component';
import { SearchAddressComponent } from './component/home/search-address/search-address.component';
import { PropertyDetailsComponent } from './component/property-details/property-details.component';
import { PropertyListComponent } from './component/property-list/property-list.component';
import { PropertyCardComponent } from './component/property-list/property-card/property-card.component';
import { ReviewsListComponent } from './component/property-details/reviews-list/reviews-list.component';
import { ReviewCardComponent } from './component/property-details/reviews-list/review-card/review-card.component';
import { CustomDatePipe } from './utilities/custom.datepipe';
import { ReviewFormComponent } from './component/review-form/review-form.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { UpdateReviewComponent } from './component/update-review/update-review.component';
import { ddMMyyyyPipe } from './utilities/ddMMyyyy.datepipe';
import { NavBarComponent } from './component/nav-bar/nav-bar.component';
import { LoginFormComponent } from './component/login-form/login-form.component';
import { RegisterFormComponent } from './component/register-form/register-form.component';
import { AuthInterceptor } from './AuthInterceptor';
import { MenubarModule } from 'primeng/menubar';
import { Button, ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SearchAddressComponent,
    PropertyDetailsComponent,
    PropertyListComponent,
    PropertyCardComponent,
    ReviewsListComponent,
    ReviewCardComponent,
    CustomDatePipe,
    ddMMyyyyPipe,
    ReviewFormComponent,
    UpdateReviewComponent,
    NavBarComponent,
    LoginFormComponent,
    RegisterFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    }),
    MenubarModule,
    ButtonModule,
    RippleModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
