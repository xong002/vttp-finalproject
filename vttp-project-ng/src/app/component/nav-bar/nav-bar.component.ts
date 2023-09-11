import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
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
  sub$!: Subscription;

  ngOnInit(){
    this.isLoggedIn = this.sessionService.isLoggedIn;
    this.sub$ = this.sessionService.onLogInLogOut.subscribe(resp => this.isLoggedIn = resp);
  }
  
  ngOnDestroy(){
    this.sub$.unsubscribe();
  }

  logout(){
    localStorage.removeItem('authToken');
    this.sessionService.setLogInStatus();
    alert("You have been logged out.");
    this.router.navigate(['/'])
  }
}
