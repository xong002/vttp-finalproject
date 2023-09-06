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
  addresslist: Property[] = []
  searchVal!: string;
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
        for (let address of (resp as any).results) {
          if (address.POSTAL !== "NIL") {
            // let a = new Property();
            let newProp = {
              id : 0,
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
            // a.searchValue = address.SEARCHVAL;
            // a.areaId = 0;
            // a.blkNo = address.BLK_NO;
            // a.roadName = address.ROAD_NAME;
            // a.building = address.BUILDING;
            // a.postal = address.POSTAL;
            // a.address = address.ADDRESS;
            // a.latitude = address.LATITUDE;
            // a.longitude = address.LONGITUDE;

            firstValueFrom(this.http.post('/api/property/create', newProp)).then(
              resp => {
                newProp.id = (resp as any).generatedPropertyId;
              }
            ).catch(() => {
            });

            this.addresslist.push(newProp);
          }
        }
        console.log(this.addresslist);
        this.onChangePropertyList.next({
          searchVal: this.searchVal,
          currentPageNum: (resp as any).pageNum,
          totalPages: (resp as any).totalNumPages,
          addressList: this.addresslist
        });
      }
    );
  }
}
