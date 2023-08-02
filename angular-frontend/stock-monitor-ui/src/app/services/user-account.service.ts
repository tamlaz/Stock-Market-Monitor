import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RegistrationModel} from "../models/registration-model";
import {Observable} from "rxjs";

const BASE_URL = 'http://localhost:8080/api/users'

@Injectable({
  providedIn: 'root'
})
export class UserAccountService {

  constructor(private http: HttpClient) { }

  registerAccount(data: RegistrationModel): Observable<any> {
    return this.http.post(`${BASE_URL}/register`,data);
  }


}
