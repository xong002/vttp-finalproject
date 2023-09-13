import { Location } from '@angular/common';
import { Component, ElementRef, Input, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Review } from 'src/app/models';
import { SessionService } from 'src/app/service/session.service';
import { SpringbootService } from 'src/app/service/springboot.service';

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent {
  fb = inject(FormBuilder);
  @Input() formGroup!: FormGroup;
  review!: Review;
  route = inject(ActivatedRoute);
  router = inject(Router)
  springbootService = inject(SpringbootService);
  sessionService = inject(SessionService);
  propertyName = '';
  location = inject(Location);

  @ViewChild('reviewFile')
  private eRef! : ElementRef;

  ngOnInit() {
    this.propertyName = this.sessionService.property.blkNo + ' ' + this.sessionService.property.roadName + ' ' + this.sessionService.property.building;
    
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
    
    let userId = localStorage.getItem('userId');
    if (userId != null) {
      r.userId = +userId
    } else return;
    
    r.propertyId = this.route.snapshot.params['propertyId'];
    r.status = 'approved';

    this.springbootService.saveReview(r, this.eRef)
      .then(() => {
        alert("Review submitted!");
        this.router.navigate(['/propertydetails'], { queryParams: { id: r.propertyId } })
      })
      .catch(error => {
        if(error.status == 403){
          alert("You must be logged in to submit a review.")
          this.router.navigate(['/login'])
        } else {
          console.log(error);
          alert("Error submitting review.")
        }
      });
  }

  back() {
    this.location.back();
  }

}
