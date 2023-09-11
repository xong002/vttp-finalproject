import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserDetailsInput } from 'src/app/models';
import { SessionService } from 'src/app/service/session.service';
import { SpringbootService } from 'src/app/service/springboot.service';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent {
  fb = inject(FormBuilder);
  formGroup!: FormGroup;
  springbootService = inject(SpringbootService);
  router = inject(Router)
  isLoggedIn!: boolean
  sessionService = inject(SessionService);

  ngOnInit() {
    if (localStorage.getItem('authToken') != null) {
      this.router.navigate(['/'])
    } else {
      this.formGroup = this.fb.group({
        email: this.fb.control('', [Validators.required, Validators.email]),
        password: this.fb.control('', Validators.required),
        displayName: this.fb.control('', [Validators.required, Validators.minLength(1), Validators.maxLength(30)])
      })
    }
  }

  processForm() {
    this.formGroup.value['displayName'].replaceAll(' ', '');
    let input: UserDetailsInput = this.formGroup.value;
    input.role = "USER";
    input.status = "active";

    this.springbootService.register(input)
      .then(() => {
        alert("You have been registered. You may log in now.")
        this.router.navigate(['/login'])
      })
      .catch(error => {
        let errMsg = error.error.error;
        let alertMsg = '';
        if (errMsg === 'duplicate email') {
          alertMsg = ' Email is taken. '
        }
        if (errMsg === 'duplicate displayName') {
          alertMsg = ' Display name is taken. '
        }
        alert("Unable to register:" + alertMsg + "Please try again.")
      })
  }
}
