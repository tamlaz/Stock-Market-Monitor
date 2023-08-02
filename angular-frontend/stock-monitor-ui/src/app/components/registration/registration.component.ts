import {Component} from '@angular/core';
import {UserAccountService} from "../../services/user-account.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {

  registrationForm: FormGroup;

  constructor(private accountService: UserAccountService, private builder: FormBuilder) {
    this.registrationForm = this.builder.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required]
    })
  }


  register() {
    const data = {...this.registrationForm.value};
    this.accountService.registerAccount(data).subscribe({
      next: () => {},
      error: err => console.log(err),
      complete: () => {
        this.registrationForm.reset();
        localStorage.setItem('auth', 'true');
      }
    })
  }
}
