import { Component, Input, inject } from '@angular/core';
import { Subscription } from 'rxjs';
import { Property } from 'src/app/models';
// import { OnemapApiService } from 'src/app/service/onemap-api.service';
import { SessionService } from 'src/app/service/session.service';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.css']
})
export class PropertyListComponent {
  addressList!: Property[];
  // onemapAPIService = inject(OnemapApiService);
  sessionService = inject(SessionService);
  sub$!: Subscription;
  searchVal!: string;
  currentPageNum!: number;
  totalPages!: number;

  ngOnInit() {
    // remove after testing
    // this.onemapAPIService.searchProperty('bedok', 1)
    // this.searchVal = this.onemapAPIService.searchVal;
    // this.currentPageNum = this.onemapAPIService.currentPageNum;
    // this.totalPages = this.onemapAPIService.totalPages;
    this.searchVal = this.sessionService.searchVal;
    this.addressList = this.sessionService.addresslist;
    console.log(this.addressList);


    // this.sub$ = this.onemapAPIService.onChangePropertyList.subscribe(resp => {
    //   this.searchVal = this.onemapAPIService.searchVal;
    //   this.currentPageNum = this.onemapAPIService.currentPageNum;
    //   this.totalPages = this.onemapAPIService.totalPages;
    //   this.addressList = (resp as any).addressList;
    // })
    
  }

  ngOnChanges() {
    this.addressList = [...this.sessionService.addresslist];
  }

  ngOnDestroy() {
    // this.sub$.unsubscribe();
  }

  // nextPage() {
  //   this.currentPageNum++;
  //   this.onemapAPIService.searchProperty(this.searchVal, this.currentPageNum)
  //   window.scroll({
  //     top: 0,
  //     left: 0,
  //     behavior: 'smooth'
  //   });
  // }

  // previousPage() {
  //   this.currentPageNum--;
  //   this.onemapAPIService.searchProperty(this.searchVal, this.currentPageNum)
  //   window.scroll({
  //     top: 0,
  //     left: 0,
  //     behavior: 'smooth'
  //   });
  // }
}
