import { Component, Input, inject } from '@angular/core';
import { Subscription } from 'rxjs';
import { Property } from 'src/app/models';
import { OnemapApiService } from 'src/app/service/onemap-api.service';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.css']
})
export class PropertyListComponent {
  addressList! : Property[];
  onemapAPIService = inject(OnemapApiService);
  sub$! : Subscription;
  searchVal! : string;
  currentPageNum!: number;
  totalPages!: number;

  ngOnInit(){
    this.sub$ = this.onemapAPIService.onChangePropertyList.subscribe(resp => {
      this.addressList = (resp as any).addressList;
      this.currentPageNum = (resp as any).currentPageNum;
      this.totalPages = (resp as any).totalPages;
      this.searchVal = (resp as any).searchVal;
    })
  }

  ngOnDestroy(){
    this.sub$.unsubscribe();
  }

  nextPage(){
    this.currentPageNum++;
    this.onemapAPIService.searchProperty(this.searchVal, this.currentPageNum)
  }

  previousPage(){
    this.currentPageNum--;
    this.onemapAPIService.searchProperty(this.searchVal, this.currentPageNum)
  }
}
