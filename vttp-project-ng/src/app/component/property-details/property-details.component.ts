import { Location } from '@angular/common';
import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Property, Review } from 'src/app/models';
import { SpringbootService } from 'src/app/service/springboot.service';

@Component({
  selector: 'app-property-details',
  templateUrl: './property-details.component.html',
  styleUrls: ['./property-details.component.css']
})
export class PropertyDetailsComponent {
  springbootService = inject(SpringbootService);
  route = inject(ActivatedRoute);
  property!: Property;
  location = inject(Location);
  reviewList : Review[] = [];


  ngOnInit() {
    this.springbootService.getProperty(this.route.snapshot.queryParams['id']).then(resp => {
      let jsonObj = JSON.parse(JSON.stringify(resp));
      this.property = jsonObj as Property;
      this.springbootService.property = this.property;

      this.springbootService.getReviewsByPropertyId(this.property.id).then(resp => {
        this.reviewList = resp as any;
      }).catch(error=>{
        console.log(error.error)
      });
    })
  }

  back() {
    this.location.back();
  }

  addPropertyImage(building: string) {
    // TODO:add file upload for image
  }
}
