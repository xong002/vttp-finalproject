import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './component/home/home.component';
import { SearchAddressComponent } from './component/home/search-address/search-address.component';
import { PropertyDetailsComponent } from './component/property-details/property-details.component';
import { PropertyListComponent } from './component/property-list/property-list.component';
import { PropertyCardComponent } from './component/property-list/property-card/property-card.component';
import { ReviewsListComponent } from './component/property-details/reviews-list/reviews-list.component';
import { ReviewCardComponent } from './component/property-details/reviews-list/review-card/review-card.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SearchAddressComponent,
    PropertyDetailsComponent,
    PropertyListComponent,
    PropertyCardComponent,
    ReviewsListComponent,
    ReviewCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
