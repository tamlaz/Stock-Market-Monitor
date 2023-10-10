import {Injectable, Optional} from '@angular/core';
import {LoginCommandModel} from "../models/login-command-model";
import {RegistrationModel} from "../models/registration-model";
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {LoginResponseModel} from "../models/login-response-model";
import jwt_decode from "jwt-decode";
import {DecodedToken} from "../models/decoded-token";
import {TokenValidationResponseModel} from "../models/token-validation-response-model";

const AUTH_BASE_URL = environment.AUTH_BASE_URL;

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  authStatus = new BehaviorSubject(null);

  constructor(private http: HttpClient) { }

  getAuthStatus() {
    return this.authStatus.asObservable();
  }

  registerAccount(data: RegistrationModel): Observable<any> {
    return this.http.post(`${AUTH_BASE_URL}/register`,data);
  }

  loginUser(data: LoginCommandModel):Observable<LoginResponseModel> {
    return this.http.post<LoginResponseModel>(`${AUTH_BASE_URL}/login`, data);
  }

  validateTokenInLStorage() {
    const token = localStorage.getItem('token');
    return (token);
  }

  validateTokenInServer(): Observable<TokenValidationResponseModel> {
    return this.http.get<TokenValidationResponseModel>(`${AUTH_BASE_URL}/validate-token`);
  }

  getDecodedToken(token:string): DecodedToken | null {
    try {
      return jwt_decode(<string>token);
    } catch (Error) {
      return null;
    }
  }
}
