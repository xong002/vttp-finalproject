import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { OnemapApiService } from 'src/app/service/onemap-api.service';
import { SessionService } from 'src/app/service/session.service';

@Component({
  selector: 'app-search-address',
  templateUrl: './search-address.component.html',
  styleUrls: ['./search-address.component.css']
})
export class SearchAddressComponent {
  fb = inject(FormBuilder);
  formGroup!: FormGroup;
  onemapAPIService = inject(OnemapApiService);
  sessionService = inject(SessionService);
  router = inject(Router);
  noResults = false;
  searchVal! : string;

  ngOnInit() {
    this.formGroup = this.fb.group({
      searchVal: this.fb.control<string>('', Validators.required),
    })
  }

  searchAddress() {
    this.searchVal = this.formGroup.value['searchVal'];
    this.sessionService.searchVal = this.searchVal;
    this.onemapAPIService.searchProperty(this.searchVal, 1).then(() => {
        console.log(this.onemapAPIService.addresslist);
        if(this.onemapAPIService.addresslist.length == 0) this.noResults = true
        else this.router.navigate(['/propertylist']);
    });
  }
}
