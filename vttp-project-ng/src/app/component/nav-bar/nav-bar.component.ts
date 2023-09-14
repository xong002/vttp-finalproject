import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { SessionService } from 'src/app/service/session.service';
import { MenuItem } from 'primeng/api';

@Component({
    selector: 'app-nav-bar',
    templateUrl: './nav-bar.component.html',
    styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent {
    isLoggedIn!: boolean;
    router = inject(Router);
    sessionService = inject(SessionService);
    isLoggedIn$!: Subscription;
    displayName!: string;
    route = inject(ActivatedRoute)
    items: MenuItem[] | undefined;

    ngOnInit() {
        this.sessionService.setLogInStatus();
        let displayName = localStorage.getItem('displayName')
        this.isLoggedIn = this.sessionService.isLoggedIn;
        if (displayName != null) this.displayName = displayName;

        this.isLoggedIn$ = this.sessionService.onLogInLogOut.subscribe(resp => {
            this.isLoggedIn = resp;
            if (displayName != null) this.displayName = displayName;
        });

        this.items = [
            {
                label: 'Search',
                icon: 'pi pi-fw pi-search',
            },
            {
                label: this.displayName,
            },
        ];



    }

    ngOnChange(){
        let displayName = localStorage.getItem('displayName')
        if (displayName != null) this.displayName = displayName;
    }

    ngOnDestroy() {
        this.isLoggedIn$.unsubscribe();
    }

    login() {
        this.sessionService.tempUrl = this.router.url;
        this.router.navigate(['/login'])
    }

    logout() {
        localStorage.removeItem('authToken');
        this.sessionService.setLogInStatus();
        this.displayName = '';
        alert("You have been logged out.");
        this.router.navigate(['/'])
    }
}
