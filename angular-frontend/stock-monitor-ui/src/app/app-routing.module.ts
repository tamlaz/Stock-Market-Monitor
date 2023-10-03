import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {StockListComponent} from "./components/stock-list/stock-list.component";
import {StockDetailsComponent} from "./components/stock-details/stock-details.component";
import {RegistrationComponent} from "./components/registration/registration.component";
import {LoginComponent} from "./components/login/login.component";

const routes: Routes = [
  {path: 'stock-list', component: StockListComponent},
  {path: 'stock-details/:id', component: StockDetailsComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo: '/stock-list'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
