import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './component/home/home.component';
import { SearchAddressComponent } from './component/home/search-address/search-address.component';
import { PropertyDetailsComponent } from './component/property-details/property-details.component';
import { AddressListComponent } from './component/home/address-list/address-list.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SearchAddressComponent,
    PropertyDetailsComponent,
    AddressListComponent
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
