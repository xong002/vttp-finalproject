import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
// import { OnemapApiService } from 'src/app/service/onemap-api.service';
import { SessionService } from 'src/app/service/session.service';
import { SpringbootService } from 'src/app/service/springboot.service';

@Component({
  selector: 'app-search-address',
  templateUrl: './search-address.component.html',
  styleUrls: ['./search-address.component.css']
})
export class SearchAddressComponent {
  fb = inject(FormBuilder);
  formGroup!: FormGroup;
  // onemapAPIService = inject(OnemapApiService);
  sessionService = inject(SessionService);
  springbootService = inject(SpringbootService);
  router = inject(Router);
  noResults = false;
  searchVal!: string;
  isLoading = false;

  ngOnInit() {
    this.formGroup = this.fb.group({
      searchVal: this.fb.control<string>('', Validators.required),
    })
  }

  searchAddress() {
    this.isLoading = true;
    this.searchVal = this.formGroup.value['searchVal'];
    this.sessionService.searchVal = this.searchVal;

    this.springbootService.searchProperty(this.searchVal).then(
      resp => {
        if ((resp as any).length == 0) {
          this.noResults = true
          this.isLoading = false;
        }
        else {
          this.sessionService.addresslist = (resp as any)
          this.router.navigate(['/propertylist']);
        }
      }).catch(error => {
        alert("Error searching for results. Please try again later.")
        this.isLoading = false;
      });

    // this.onemapAPIService.searchProperty(this.searchVal, 1).then(() => {
    //     console.log(this.onemapAPIService.addresslist);
    //     if(this.onemapAPIService.addresslist.length == 0) this.noResults = true
    //     else this.router.navigate(['/propertylist']);
    // }).catch(error => {
    //   alert("Error searching for results. Please try again later.")
    //   this.isLoading = false;
    // });
  }
}
