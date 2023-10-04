import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from "@angular/common/http";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { StockListComponent } from './components/stock-list/stock-list.component';
import { StockDetailsComponent } from './components/stock-details/stock-details.component';
import { RegistrationComponent } from './components/registration/registration.component';
import {ReactiveFormsModule} from "@angular/forms";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { LoginComponent } from './components/login/login.component';
import { StockFormComponent } from './components/stock-form/stock-form.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    StockListComponent,
    StockDetailsComponent,
    RegistrationComponent,
    LoginComponent,
    StockFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FontAwesomeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
