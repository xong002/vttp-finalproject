import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Subject, firstValueFrom } from 'rxjs';
import { Property, PropertyResponse } from '../models';

@Injectable({
  providedIn: 'root'
})
export class OnemapApiService {
  http = inject(HttpClient);
  url = "https://www.onemap.gov.sg/api/common/elastic/search"
  searchVal!: string;
  addresslist: Property[] = []
  currentPageNum!: number;
  totalPages!: number;

  onChangePropertyList = new Subject<PropertyResponse>();

  searchProperty(searchVal: string, pageNum: number) {
    this.addresslist = [];
    this.searchVal = searchVal;

    let queryParams = new HttpParams();
    queryParams = queryParams.append("searchVal", searchVal.replaceAll(' ', '+'));
    queryParams = queryParams.append("returnGeom", "Y");
    queryParams = queryParams.append("getAddrDetails", "Y");
    queryParams = queryParams.append("pageNum", pageNum);

    return firstValueFrom(this.http.get(this.url, { params: queryParams })).then(
      resp => {
        this.currentPageNum = (resp as any).pageNum;
        this.totalPages = (resp as any).totalNumPages;

        const propList = [];
        for (let address of (resp as any).results) {
          if (address.POSTAL !== "NIL") {
            let newProp = {
              id: 0,
              images: 'https://csf-xz.sgp1.digitaloceanspaces.com/images/image-not-found.jpg',
              areaId: 0,
              searchValue: address.SEARCHVAL,
              blkNo: address.BLK_NO,
              roadName: address.ROAD_NAME,
              building: address.BUILDING,
              address: address.ADDRESS,
              postal: address.POSTAL,
              latitude: address.LATITUDE,
              longitude: address.LONGITUDE
            }
            propList.push(newProp);
          }
        }

        return firstValueFrom(this.http.post('/api/property/createbatch', propList)).then(
          resp => {
            this.addresslist = (resp as any);
            console.log(this.addresslist)
            
            this.onChangePropertyList.next({
              searchVal: this.searchVal,
              currentPageNum: (resp as any).pageNum,
              totalPages: (resp as any).totalNumPages,
              addressList: this.addresslist
            });
          }
        )


      }
    )


  }
}
