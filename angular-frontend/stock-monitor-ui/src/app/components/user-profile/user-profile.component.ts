import { Component } from '@angular/core';
import {UserProfileDetailsModel} from "../../models/user-profile-details-model";
import {UserService} from "../../services/user-service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent {

  userProFileDetails: UserProfileDetailsModel;
  chosenSection: string = '';

  constructor(private userService: UserService) {
    this.ngOnInit();
  }

  ngOnInit() {
    this.userService.getUserProfileDetails().subscribe({
      next: data => this.userProFileDetails = data,
    })
  }

  chooseDefault() {
    this.chosenSection = '';
  }

  chooseBalance() {
    this.chosenSection = 'balance';
  }

  chooseWatchList() {
    this.chosenSection = 'watchlist';
  }

  choosePortfolio() {
    this.chosenSection = 'portfolio';
  }
}
