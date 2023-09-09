import { Component, Input } from '@angular/core';
import { Review } from 'src/app/models';

@Component({
  selector: 'app-review-card',
  templateUrl: './review-card.component.html',
  styleUrls: ['./review-card.component.css']
})
export class ReviewCardComponent {
  @Input() review!: Review;
}
