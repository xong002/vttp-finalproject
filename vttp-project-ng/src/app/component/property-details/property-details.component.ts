import { Location } from '@angular/common';
import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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


  ngOnInit() {
    this.springbootService.getProperty(this.route.snapshot.queryParams['id']).then(resp => {
      let jsonObj = JSON.parse(JSON.stringify(resp));
      this.property = jsonObj as Property;
      this.sessionService.property = this.property;

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
