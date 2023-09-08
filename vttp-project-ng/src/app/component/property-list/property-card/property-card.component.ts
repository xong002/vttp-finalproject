import { Component, Input } from '@angular/core';
import { Property } from 'src/app/models';

@Component({
  selector: 'app-property-card',
  templateUrl: './property-card.component.html',
  styleUrls: ['./property-card.component.css']
})
export class PropertyCardComponent {
  @Input() property!: Property;
}
