import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { KevComponent } from './kev/kev.component';
import {HttpClient,HttpClientModule} from '@angular/common/http'

@NgModule({
  declarations: [
    AppComponent,
    KevComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [
  HttpClient
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
