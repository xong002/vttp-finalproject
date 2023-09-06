import { Component, inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { OnemapApiService } from 'src/app/service/onemap-api.service';

@Component({
  selector: 'app-search-address',
  templateUrl: './search-address.component.html',
  styleUrls: ['./search-address.component.css']
})
export class SearchAddressComponent {
  searchVal! : FormControl;
  onemapAPIService = inject(OnemapApiService);
  router = inject(Router);

  ngOnInit() {
    this.searchVal = new FormControl('', Validators.required);
  }

  searchAddress() {
    console.log(this.searchVal);
    this.onemapAPIService.searchAddress(this.searchVal.value).then((resp => console.log(resp)))
    this.router.navigate(['/propertydetails']);
  }
}
