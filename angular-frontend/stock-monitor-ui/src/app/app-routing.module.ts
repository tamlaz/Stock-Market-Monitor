import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {StockListComponent} from "./components/stock-list/stock-list.component";
import {StockDetailsComponent} from "./components/stock-details/stock-details.component";
import {RegistrationComponent} from "./components/registration/registration.component";
import {LoginComponent} from "./components/login/login.component";
import {StockFormComponent} from "./components/stock-form/stock-form.component";
import {auth} from "./guards/auth-guard";
import {HasRoleGuard} from "./guards/has-role-guard";
import {UserProfileComponent} from "./components/user-profile/user-profile.component";

const routes: Routes = [
  {path: 'stock-list', component: StockListComponent},
  {path: 'stock-form', component: StockFormComponent, canActivate: [() => auth(), HasRoleGuard],
  data: {
    expectedRoles: ['ADMIN']
  }},
  {path: 'stock-details/:id', component: StockDetailsComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'login', component: LoginComponent},
  {path: 'user-profile', component: UserProfileComponent},
  {path: '**', redirectTo: '/stock-list'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
