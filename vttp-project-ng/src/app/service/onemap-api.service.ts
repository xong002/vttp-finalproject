import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OnemapApiService {
  http = inject(HttpClient);
  url = "https://www.onemap.gov.sg/api/common/elastic/search"
  // addresslist : Address[] = []

  searchAddress(searchVal : string){
    let queryParams = new HttpParams();
    queryParams = queryParams.append("searchVal", searchVal);
    queryParams = queryParams.append("returnGeom", "Y");
    queryParams = queryParams.append("getAddrDetails", "Y");

    return firstValueFrom(this.http.get(this.url, {params : queryParams }));
  }
}
