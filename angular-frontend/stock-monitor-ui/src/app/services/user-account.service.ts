import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RegistrationModel} from "../models/registration-model";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.development";
import {UserProfileDetailsModel} from "../models/user-profile-details-model";

const USER_BASE_URL = environment.USER_BASE_URL;

@Injectable({
  providedIn: 'root'
})
export class UserAccountService {

  constructor(private http: HttpClient) { }


  getUserProfileDetails() {
    return this.http.get<UserProfileDetailsModel>(`${USER_BASE_URL}`);
  }




}
