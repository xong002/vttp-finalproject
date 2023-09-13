import { Location } from '@angular/common';
import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Review } from 'src/app/models';
import { SessionService } from 'src/app/service/session.service';
import { SpringbootService } from 'src/app/service/springboot.service';

@Component({
  selector: 'app-update-review',
  templateUrl: './update-review.component.html',
  styleUrls: ['./update-review.component.css']
})
export class UpdateReviewComponent {
  fb = inject(FormBuilder);
  formGroup!: FormGroup;
  review!: Review;
  route = inject(ActivatedRoute);
  router = inject(Router)
  springbootService = inject(SpringbootService);
  location = inject(Location);
  reviewId! : string;
 
  @ViewChild('reviewFile')
  private eRef! : ElementRef;

  ngOnInit() {
    this.reviewId = this.route.snapshot.queryParams['reviewId'];

    this.springbootService.getReviewById(this.reviewId).then(resp => {
      this.review = resp as Review;

      this.formGroup = this.fb.group({
        title: this.fb.control<string>(this.review.title, Validators.required),
        monthlyRentalCost: this.fb.control(this.review.monthlyRentalCost),
        floor: this.fb.control<string>(this.review.floor),
        apartmentFloorArea: this.fb.control(this.review.apartmentFloorArea),
        rentalFloorArea: this.fb.control(this.review.rentalFloorArea),
        furnishings: this.fb.control<string>(this.review.furnishings),
        sharedToilet: this.fb.control<boolean>(this.review.sharedToilet),
        rules: this.fb.control<string>(this.review.rules),
        rentalStartDate: this.fb.control<string>(this.review.rentalStartDate == null ? '' : this.formatDate(this.review.rentalStartDate)),
        rentalDuration: this.fb.control(this.review.rentalDuration),
        occupants: this.fb.control<number>(this.review.occupants, [Validators.min(1)]),
        rating: this.fb.control<number>(this.review.rating, [Validators.required, Validators.min(1), Validators.max(5)]),
        comments: this.fb.control<string>(this.review.comments, Validators.required)
      })
    })
  }

  processForm() {
    let r: Review = this.formGroup.value;
    r.id = this.reviewId;
    r.userId = this.review.userId;
    r.propertyId = this.review.propertyId;
    r.status = this.review.status;

    this.springbootService.updateReview(r, this.eRef)
      .then(() => {
        alert("Review submitted!");
        this.router.navigate(['/propertydetails'], { queryParams: { id: r.propertyId } })
      })
      .catch(error => {
        console.log(error);
        alert("Error updating review.")
      });
  }

  back() {
    this.location.back();
  }

  formatDate(date: string) {
    let d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;

    return [year, month, day].join('-');
}
}
