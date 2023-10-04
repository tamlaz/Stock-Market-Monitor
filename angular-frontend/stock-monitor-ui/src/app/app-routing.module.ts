import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {StockListComponent} from "./components/stock-list/stock-list.component";
import {StockDetailsComponent} from "./components/stock-details/stock-details.component";
import {RegistrationComponent} from "./components/registration/registration.component";
import {LoginComponent} from "./components/login/login.component";
import {StockFormComponent} from "./components/stock-form/stock-form.component";

const routes: Routes = [
  {path: 'stock-list', component: StockListComponent},
  {path: 'stock-form', component: StockFormComponent},
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
