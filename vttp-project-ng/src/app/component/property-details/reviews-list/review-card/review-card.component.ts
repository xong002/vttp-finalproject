import { Component, Input } from '@angular/core';
import { Review } from 'src/app/models';

@Component({
  selector: 'app-review-card',
  templateUrl: './review-card.component.html',
  styleUrls: ['./review-card.component.css']
})
export class ReviewCardComponent {
  @Input() review!: Review;
  userId!: number;
  images: string[] = [];

  ngOnInit() {
    this.userId = +localStorage.getItem('userId')!

    if (this.review.images != null) {
      let arr = this.review.images.trim().split(' ');
      arr.forEach(string => {
        this.images.push(string.trim());
        console.log(string.trim())
      })
    }

  }
}
