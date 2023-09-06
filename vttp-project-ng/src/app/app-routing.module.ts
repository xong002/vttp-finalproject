import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { PropertyDetailsComponent } from './component/property-details/property-details.component';

const routes: Routes = [
  { path : '', component: HomeComponent},
  { path : 'propertydetails', component: PropertyDetailsComponent},
  // { path : '**', redirectTo: '/', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
