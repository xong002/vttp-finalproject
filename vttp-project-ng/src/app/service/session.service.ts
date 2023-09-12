import { Injectable } from '@angular/core';
import { Property } from '../models';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  property!: Property;
  searchVal!: string;
  isLoggedIn!: boolean;
  onLogInLogOut = new Subject<boolean>;
  // onDisplayNameChange = new Subject<string>;
  // displayName!: string;
  // userId!: number;

  setLogInStatus() {
    if (localStorage.getItem('authToken') == null) {
      this.isLoggedIn = false;
      localStorage.removeItem('displayName')
      localStorage.removeItem('userId')
    } else if (localStorage.getItem('authToken') != null) {
      this.isLoggedIn = true;
      console.log("logged in")
    }
    this.onLogInLogOut.next(this.isLoggedIn);
    // this.onDisplayNameChange.next(this.displayName);
  }
}
