import { HttpClient } from '@angular/common/http';
import { ElementRef, Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { AuthenticationRequest, Review, UserDetailsInput } from '../models';

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

  getReviewsByBuildingName(building: string, propertyId: number, limit: number, offset: number) {
    return firstValueFrom(this.http.get('/api/review', { params: { building: building, optPropertyIdExcluded: propertyId, optLimit: limit, optOffset: offset } }))
  }

  saveReview(review: Review) {
    return firstValueFrom(this.http.post('/api/review/create', review))
  }

  getReviewById(id: string) {
    return firstValueFrom(this.http.get('/api/review', { params: { id: id } }))
  }

  updateReview(review: Review) {
    return firstValueFrom(this.http.put('/api/review/update', review))
  }

  login(request: AuthenticationRequest) {
    return firstValueFrom(this.http.post('/api/user/authenticate', request))
  }

  register(input: UserDetailsInput) {
    return firstValueFrom(this.http.post('/api/user/create', input))
  }

  uploadPropertyImage(userName: string, propertyId: number, eRef: ElementRef) {
    let formData = new FormData();
    formData.set('userName', userName);
    formData.set('propertyId', propertyId.toString())
    formData.set('file', eRef.nativeElement.files[0]);
    return firstValueFrom(this.http.post('/api/property/upload', formData))
  }
}
