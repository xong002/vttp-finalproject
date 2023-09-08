import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { OnemapApiService } from 'src/app/service/onemap-api.service';

@Component({
  selector: 'app-search-address',
  templateUrl: './search-address.component.html',
  styleUrls: ['./search-address.component.css']
})
export class SearchAddressComponent {
  fb = inject(FormBuilder);
  formGroup!: FormGroup;
  onemapAPIService = inject(OnemapApiService);
  router = inject(Router);
  noResults = false;

  ngOnInit() {
    this.formGroup = this.fb.group({
      searchVal: this.fb.control<string>('', Validators.required),
    })
  }

  searchAddress() {
    this.onemapAPIService.searchProperty(this.formGroup.value['searchVal'], 1).then(() => {
        console.log(this.onemapAPIService.addresslist);
        if(this.onemapAPIService.addresslist.length == 0) this.noResults = true
        else this.router.navigate(['/propertylist']);
    });
  }
}
