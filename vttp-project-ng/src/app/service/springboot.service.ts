import { HttpBackend, HttpClient } from '@angular/common/http';
import { ElementRef, Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { AuthenticationRequest, Review, UserDetailsInput } from '../models';

@Injectable({
  providedIn: 'root'
})
export class SpringbootService {
  http = inject(HttpClient);

  // httpClient: HttpClient;

  // constructor( handler: HttpBackend) { 
  //    this.httpClient = new HttpClient(handler);
  // }

  getProperty(id: number) {
    return firstValueFrom(this.http.get<string>('/api/property', { params: { id: id } }))
  }

  getReviewsByPropertyId(propertyId: number) {
    return firstValueFrom(this.http.get('/api/review', { params: { propertyId: propertyId } }))
  }

  getReviewsByBuildingName(building: string, propertyId: number, limit: number, offset: number) {
    return firstValueFrom(this.http.get('/api/review', { params: { building: building, optPropertyIdExcluded: propertyId, optLimit: limit, optOffset: offset } }))
  }

  saveReview(r: Review, eRef: ElementRef) {
    let formData = new FormData();
    formData.set('userId', r.userId.toString());
    formData.set('propertyId', r.propertyId.toString());
    formData.set('title', r.title);
    formData.set('monthlyRentalCost', r.monthlyRentalCost.toString());
    formData.set('floor', r.floor);
    formData.set('apartmentFloorArea', r.apartmentFloorArea);
    formData.set('rentalFloorArea', r.rentalFloorArea);
    formData.set('furnishings', r.furnishings);
    formData.set('sharedToilet', String(r.sharedToilet));
    formData.set('rules', r.rules);
    formData.set('rentalStartDate', r.rentalStartDate);
    formData.set('rentalDuration', r.rentalDuration.toString());
    formData.set('occupants', r.occupants.toString());
    formData.set('rating', r.rating.toString());
    formData.set('comments', r.comments);
    formData.set('status', r.status);
    for (let i = 0; i < eRef.nativeElement.files.length; i++) {
      formData.append('files', eRef.nativeElement.files[i]);
    }
    return firstValueFrom(this.http.post('/api/review/create', formData))
  }

  getReviewById(id: string) {
    return firstValueFrom(this.http.get('/api/review', { params: { id: id } }))
  }

  updateReview(r: Review, eRef: ElementRef) {
    let formData = new FormData();
    formData.set('id', r.id);
    formData.set('userId', r.userId.toString());
    formData.set('propertyId', r.propertyId.toString());
    formData.set('title', r.title);
    formData.set('monthlyRentalCost', r.monthlyRentalCost.toString());
    formData.set('floor', r.floor);
    formData.set('apartmentFloorArea', r.apartmentFloorArea);
    formData.set('rentalFloorArea', r.rentalFloorArea);
    formData.set('furnishings', r.furnishings);
    formData.set('sharedToilet', String(r.sharedToilet));
    formData.set('rules', r.rules);
    formData.set('rentalStartDate', r.rentalStartDate);
    formData.set('rentalDuration', r.rentalDuration.toString());
    formData.set('occupants', r.occupants.toString());
    formData.set('rating', r.rating.toString());
    formData.set('comments', r.comments);
    formData.set('status', r.status);
    for (let i = 0; i < eRef.nativeElement.files.length; i++) {
      formData.append('files', eRef.nativeElement.files[i]);
    }
    // console.log(formData.get('files'));
    // return null;
    return firstValueFrom(this.http.put('/api/review/update', formData))
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
