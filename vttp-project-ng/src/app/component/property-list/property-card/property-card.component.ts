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

  goToPropertyDetails() {
    this.router.navigate(['/propertydetails'], { queryParams: { id: this.property.id } })
  }
}
