import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";

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
import {AuthInterceptor} from "./services/authInterceptor";
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { StockPurchaseFormComponent } from './components/stock-purchase-form/stock-purchase-form.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    StockListComponent,
    StockDetailsComponent,
    RegistrationComponent,
    LoginComponent,
    StockFormComponent,
    UserProfileComponent,
    StockPurchaseFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FontAwesomeModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
