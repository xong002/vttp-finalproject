import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationRequest } from 'src/app/models';
import { SessionService } from 'src/app/service/session.service';
import { SpringbootService } from 'src/app/service/springboot.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {
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
        password: this.fb.control('', Validators.required)
      })
    }
  }

  processForm() {
    let request: AuthenticationRequest = this.formGroup.value;
    this.springbootService.login(request)
      .then((resp) => {
        let token = (resp as any).token;
        if (token != null) {
          // this.sessionService.userId = (resp as any).userId;
          // this.sessionService.displayName = (resp as any).displayName;
          
          localStorage.setItem('authToken', token);
          localStorage.setItem('userId', (resp as any).userId);
          localStorage.setItem('displayName', (resp as any).displayName)
          this.sessionService.setLogInStatus();

          alert("Logged in!");
          this.router.navigate(['/'])
        } else {
          localStorage.removeItem('authToken')
          this.sessionService.setLogInStatus();
        }

      })
      .catch(error => alert("Incorrect username/password. Please try again."))
  }

}
