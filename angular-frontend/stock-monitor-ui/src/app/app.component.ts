import { Component } from '@angular/core';
import {DecodedToken} from "./models/decoded-token";
import {AuthenticationService} from "./services/authentication.service";
import jwt_decode from "jwt-decode";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'stock-monitor-ui';

  constructor(private auth: AuthenticationService) {
  }

  getDecodedAccessToken = (token: string): DecodedToken => {
    try {
      return jwt_decode(token);
    } catch (Error) {
      return null;
    }
  }

  ngOnInit() {
    const token = localStorage.getItem('token');

    if (token) {
      const tokenInfo = this.getDecodedAccessToken(token);
      const expireDate = tokenInfo.exp;
      const currentDate = Math.floor(Date.now() / 1000);
      if (currentDate > expireDate) {
        localStorage.removeItem('token');
      }
    }

    this.auth.validateTokenInServer().subscribe({
      error: err => {
        if (err.status === 401) {
          localStorage.removeItem('token')
        }
      }
    })
  }
}
