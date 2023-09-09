import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Review } from 'src/app/models';

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent {
  fb = inject(FormBuilder);
  formGroup!: FormGroup;
  review! : Review;

  ngOnInit(){
    this.fb.group({
      title: this.fb.control<string>('', Validators.required),
      monthlyRentalCost: this.fb.control<number>(0),
    })
  }

}
