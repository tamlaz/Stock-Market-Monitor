import {Injectable, Optional} from '@angular/core';
import {LoginCommandModel} from "../models/login-command-model";
import {RegistrationModel} from "../models/registration-model";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {LoginResponseModel} from "../models/login-response-model";

const AUTH_BASE_URL = environment.AUTH_BASE_URL;

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) { }

  registerAccount(data: RegistrationModel): Observable<any> {
    return this.http.post(`${AUTH_BASE_URL}/register`,data);
  }

  loginUser(data: LoginCommandModel):Observable<LoginResponseModel> {
    return this.http.post<LoginResponseModel>(`${AUTH_BASE_URL}/login`, data);
  }
}
