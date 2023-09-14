import { Location } from '@angular/common';
import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Property, Review } from 'src/app/models';
// import { OnemapApiService } from 'src/app/service/onemap-api.service';
import { SessionService } from 'src/app/service/session.service';
import { SpringbootService } from 'src/app/service/springboot.service';

@Component({
  selector: 'app-property-details',
  templateUrl: './property-details.component.html',
  styleUrls: ['./property-details.component.css']
})
export class PropertyDetailsComponent {
  springbootService = inject(SpringbootService);
  // onemapAPIService = inject(OnemapApiService);
  sessionService = inject(SessionService)
  route = inject(ActivatedRoute);
  property!: Property;
  location = inject(Location);
  reviewList: Review[] = [];
  router = inject(Router);
  mapURL!: SafeResourceUrl;
  sanitizer = inject(DomSanitizer);
  isLoggedIn!: boolean;
  isLoggedin$!: Subscription;
  buildingReviewList: Review[] = [];
  noResults = false;
  showFileInputBox = false;
  isLoading = false;

  @ViewChild('propertyImageFile')
  private eRef!: ElementRef;

  ngOnInit() {
    this.isLoading = true;

    this.springbootService.getProperty(this.route.snapshot.queryParams['id']).then(resp => {
      let jsonObj = JSON.parse(JSON.stringify(resp));
      this.property = jsonObj as Property;
      this.sessionService.property = this.property;
      this.mapURL = this.sanitizer.bypassSecurityTrustResourceUrl("https://www.onemap.gov.sg/minimap/minimap.html?mapStyle=Default&zoomLevel=15&latLng=" + this.property.latitude + "," + this.property.longitude + "&popupWidth=200&showPopup=false")

      this.isLoggedIn = this.sessionService.isLoggedIn;
      this.isLoggedin$ = this.sessionService.onLogInLogOut.subscribe(resp => this.isLoggedIn = resp)

      this.springbootService.getReviewsByPropertyId(this.property.id).then(resp => {
        this.reviewList = resp as any;
        this.isLoading = false;

        // this.springbootService.getReviewsByBuildingName(this.property.building, this.property.id, 5, 0).then(resp => {
        //   this.buildingReviewList = resp as any;
        //   this.isLoading = false;
        // })

      }).catch(error => {
        console.log(error.error)
        this.isLoading = false;
      });
    })

  }

  processForm() {
    if (!this.isLoggedIn) {
      alert("You must be logged in to write a review.");
      return;
    }

    this.router.navigate(['/reviewform/', this.route.snapshot.queryParams['id']])
  }

  back() {
    this.router.navigate(['/propertylist'])
  }

  showFileUpload() {
    this.showFileInputBox = !this.showFileInputBox
  }

  uploadFile() {
    let url = this.router.url
    this.springbootService.uploadPropertyImage(
      localStorage.getItem('displayName')!,
      this.property.id,
      this.eRef)
      .then(() => {

          alert("Image uploaded!");
          this.router.navigateByUrl(url);


      })
      .catch(error => {
        alert("Error uploading image.")
      })
  }

  searchAddress(building: string) {
    this.springbootService.searchProperty(building)
      .then(
        resp => {
          if ((resp as any).length == 0) this.noResults = true
          else {
            this.sessionService.addresslist = (resp as any);
            this.router.navigate(['/propertylist']);
          }
        })
  }

  viewChart(building : string){
    this.sessionService.tempUrl = this.router.url;
    this.router.navigate(['/chart/', building]);
  }
}
