import {Component} from '@angular/core';
import {UserAccountService} from "../../services/user-account.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {validationHandler} from "../utils/validation-handler";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {

  registrationForm: FormGroup;

  constructor(private accountService: UserAccountService, private builder: FormBuilder) {
    this.registrationForm = this.builder.group({
      email: ['', [
        Validators.required,
        Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')
      ]],
      password: ['', Validators.required],
      firstName: ['', [
        Validators.required,
        Validators.pattern('^[A-Z]+[a-z]+')
      ]],
      lastName: ['', [
        Validators.required,
        Validators.pattern('^[A-Z]+[a-z]+')
      ]]
    })
  }


  register() {
    const data = {...this.registrationForm.value};
    this.accountService.registerAccount(data).subscribe({
      next: () => {},
      error: error => {
        validationHandler(error, this.registrationForm);
        console.log(error);
      },
      complete: () => {
        this.registrationForm.reset();
        localStorage.setItem('auth', 'true');
      }
    })
  }
}
