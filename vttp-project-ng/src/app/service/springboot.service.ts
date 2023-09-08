import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import {firstValueFrom} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SpringbootService {
  http = inject(HttpClient);
  // property! : Property;

  getProperty(id : number){
    return firstValueFrom(this.http.get<string>('/api/property', {params: { id : id}}))
  }

  

}
