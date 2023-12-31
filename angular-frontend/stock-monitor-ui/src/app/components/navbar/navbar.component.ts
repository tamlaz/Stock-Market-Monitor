import { Component } from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  isStockFormVisible = false;

  constructor(private auth: AuthenticationService) {
    this.ngOnInit();
  }

  ngOnInit() {
    this.auth.getAuthStatus().subscribe({
      next: data => {
        this.checkToken(data);
      }
    })
    this.checkToken(localStorage.getItem('token'));
  }

  checkToken(token:string) {
    if (token) {
      const decodedToken = this.auth.getDecodedToken(token);
      const roles = decodedToken.roles.split(" ");
      this.isStockFormVisible = roles.includes('ADMIN');
    } else {
      this.isStockFormVisible = false;
    }

  }

}
