import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { Review } from '../models';

@Injectable({
  providedIn: 'root'
})
export class SpringbootService {
  http = inject(HttpClient);

  getProperty(id: number) {
    return firstValueFrom(this.http.get<string>('/api/property', { params: { id: id } }))
  }

  getReviewsByPropertyId(propertyId: number) {
    return firstValueFrom(this.http.get('/api/review', { params: { propertyId: propertyId } }))
  }

  saveReview(review : Review){
    return firstValueFrom(this.http.post('/api/review/create', review))
  }

  getReviewById(id : string){
    return firstValueFrom(this.http.get('/api/review', { params: { id: id } }))
  }

  updateReview(review: Review){
    return firstValueFrom(this.http.put('/api/review/update', review))
  }
}
