import { Location } from '@angular/common';
import { Component, inject } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Property, Review } from 'src/app/models';
import { SessionService } from 'src/app/service/session.service';
import { SpringbootService } from 'src/app/service/springboot.service';

@Component({
  selector: 'app-property-details',
  templateUrl: './property-details.component.html',
  styleUrls: ['./property-details.component.css']
})
export class PropertyDetailsComponent {
  springbootService = inject(SpringbootService);
  sessionService = inject(SessionService)
  route = inject(ActivatedRoute);
  property!: Property;
  location = inject(Location);
  reviewList : Review[] = [];
  router = inject(Router);
  mapURL!: SafeResourceUrl;
  sanitizer = inject(DomSanitizer);
  isLoggedIn!: boolean;
  isLoggedin$! : Subscription;


  ngOnInit() {
    this.springbootService.getProperty(this.route.snapshot.queryParams['id']).then(resp => {
      let jsonObj = JSON.parse(JSON.stringify(resp));
      this.property = jsonObj as Property;
      this.sessionService.property = this.property;
      this.mapURL = this.sanitizer.bypassSecurityTrustResourceUrl("https://www.onemap.gov.sg/minimap/minimap.html?mapStyle=Default&zoomLevel=15&latLng=" + this.property.latitude + "," + this.property.longitude + "&popupWidth=200&showPopup=false")
      
      this.isLoggedIn = this.sessionService.isLoggedIn;
      this.isLoggedin$ = this.sessionService.onLogInLogOut.subscribe(resp => this.isLoggedIn = resp)

      this.springbootService.getReviewsByPropertyId(this.property.id).then(resp => {
        this.reviewList = resp as any;
      }).catch(error=>{
        console.log(error.error)
      });
    })
    
  }

  back() {
    this.router.navigate(['/propertylist'])
  }

  addPropertyImage(building: string) {
    // TODO:add file upload for image
  }
}
