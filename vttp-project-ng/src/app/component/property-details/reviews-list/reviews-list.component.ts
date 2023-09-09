import { Component, Input } from '@angular/core';
import { Review } from 'src/app/models';

@Component({
  selector: 'app-reviews-list',
  templateUrl: './reviews-list.component.html',
  styleUrls: ['./reviews-list.component.css']
})
export class ReviewsListComponent {
  @Input() reviewList!: Review[];
}
