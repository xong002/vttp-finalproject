import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { PropertyDetailsComponent } from './component/property-details/property-details.component';
import { PropertyListComponent } from './component/property-list/property-list.component';
import { ReviewFormComponent } from './component/review-form/review-form.component';
import { UpdateReviewComponent } from './component/update-review/update-review.component';
import { LoginFormComponent } from './component/login-form/login-form.component';
import { RegisterFormComponent } from './component/register-form/register-form.component';

const routes: Routes = [
  { path : '', component: HomeComponent},
  { path : 'login', component: LoginFormComponent},
  { path : 'register', component: RegisterFormComponent},
  { path : 'propertylist', component: PropertyListComponent},
  { path : 'propertydetails', component: PropertyDetailsComponent},
  { path : 'reviewform/:propertyId', component: ReviewFormComponent},
  { path : 'updatereview', component: UpdateReviewComponent},
  { path : '**', redirectTo: '/', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
