import { Injectable } from '@angular/core';
import { Property } from '../models';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  property! : Property;
  searchVal! : string;
}
