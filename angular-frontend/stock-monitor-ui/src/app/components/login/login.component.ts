import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";
import {validationHandler} from "../utils/validation-handler";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  isLoginFailed: boolean = false;

  constructor(private authService: AuthenticationService,
              private formBuilder: FormBuilder,
              private router: Router) {
    this.loginForm = formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
  }


  login() {
    this.authService.loginUser(this.loginForm.value).subscribe({
      next: data => {
        localStorage.setItem('token', data.jwt);
      },
      error: err => {
        validationHandler(err, this.loginForm);
        this.isLoginFailed = true;
      },
      complete: () => {
        this.loginForm.reset();
        this.router.navigate(['stock-list'])
      }
    });
  }

}
