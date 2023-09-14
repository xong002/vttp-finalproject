import { Component, Input, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Property } from 'src/app/models';

@Component({
  selector: 'app-property-card',
  templateUrl: './property-card.component.html',
  styleUrls: ['./property-card.component.css']
})
export class PropertyCardComponent {
  @Input() property!: Property;
  router = inject(Router);
  header = '';
  building = '';
  reviewCount = 0;

  ngOnInit(){
    this.header = this.property.blkNo + ' ' + this.toTitleCase(this.property.roadName)
    this.building = this.property.building.toUpperCase();
    this.reviewCount = this.property.reviewCount != null ? this.property.reviewCount : 0;
  }

  goToPropertyDetails() {
    this.router.navigate(['/propertydetails'], { queryParams: { id: this.property.id } })

  }

  toTitleCase(str: string) {
    return str.toLowerCase().split(' ').map(function (word) {
      return (word.charAt(0).toUpperCase() + word.slice(1));
    }).join(' ');
  }
}
