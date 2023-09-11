import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/service/session.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent {
  isLoggedIn! : boolean;
  router = inject(Router);
  sessionService = inject(SessionService);

  ngOnInit(){
    this.isLoggedIn = this.sessionService.isLoggedIn;
    this.sessionService.onLogInLogOut.subscribe(resp => this.isLoggedIn = resp);
  }

  logout(){
    localStorage.removeItem('authToken');
    this.sessionService.setLogInStatus();
    alert("You have been logged out.");
    this.router.navigate(['/'])
  }
}
