import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Property } from 'src/app/models';
import { SpringbootService } from 'src/app/service/springboot.service';

@Component({
  selector: 'app-property-details',
  templateUrl: './property-details.component.html',
  styleUrls: ['./property-details.component.css']
})
export class PropertyDetailsComponent {
  springbootService = inject(SpringbootService);
  route = inject(ActivatedRoute);
  property! : Property;

  ngOnInit(){
    this.springbootService.getProperty(this.route.snapshot.queryParams['id']).then((resp)=> {
      let jsonObj = JSON.parse(JSON.stringify(resp));
      this.property = jsonObj as Property;
    })
  }
}
