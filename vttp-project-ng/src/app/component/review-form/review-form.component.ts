import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Property, Review } from 'src/app/models';
import { SpringbootService } from 'src/app/service/springboot.service';

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent {
  fb = inject(FormBuilder);
  formGroup!: FormGroup;
  review!: Review;
  route = inject(ActivatedRoute);
  router = inject(Router)
  springbootService = inject(SpringbootService);
  propertyName = '';

  ngOnInit() {
    this.propertyName = this.springbootService.property.blkNo + ' ' + this.springbootService.property.roadName + ' ' + this.springbootService.property.building;
    
    this.formGroup = this.fb.group({
      title: this.fb.control<string>('', Validators.required),
      monthlyRentalCost: this.fb.control(''),
      floor: this.fb.control<string>(''),
      apartmentFloorArea: this.fb.control(''),
      rentalFloorArea: this.fb.control(''),
      furnishings: this.fb.control<string>(''),
      sharedToilet: this.fb.control<boolean>(false),
      rules: this.fb.control<string>(''),
      rentalStartDate: this.fb.control<string>(''),
      rentalDuration: this.fb.control(''),
      occupants: this.fb.control<number>(1, [Validators.min(1)]),
      rating: this.fb.control<number>(1, [Validators.required, Validators.min(1), Validators.max(5)]),
      comments: this.fb.control<string>('', Validators.required)
    })
    
  }

  processForm() {
    let r: Review = this.formGroup.value;
    r.userId = 1; //TODO: assign userid = 1 for testing
    r.propertyId = this.route.snapshot.params['propertyId'];
    r.status = 'approved';

    this.springbootService.saveReview(r)
      .then(() => {
        alert("Review submitted!");
        this.router.navigate(['/propertydetails'], { queryParams: { id: r.propertyId } })
      })
      .catch(error => {
        console.log(error);
        alert("Error submitting review.")
      });
  }

}
