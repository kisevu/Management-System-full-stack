import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationRequest } from '../../services/models/authentication-request';
import {KeycloakService} from '../../services/keycloak/keycloak.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{

  authRequest: AuthenticationRequest = {email:'',password:''};
  errorMsg: Array<string>=[];

   constructor(
   private router: Router,
   private keycloakService: KeycloakService
   ){}

    async ngOnInit(){
     await this.keycloakService.init();
     await this.keycloakService.login();
     }
}
