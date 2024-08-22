import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';
import {UserProfile} from './user-profile';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {
  private _keycloak: Keycloak | undefined;
  private _profile: UserProfile | undefined;

   constructor() {}

   get keycloak(){
    if(!this._keycloak){
     //if undefined
      this._keycloak = new Keycloak({
      url: 'http://localhost:9090',
      realm: 'book-social-network',
      clientId: 'bsn'
              });
       }
      return this._keycloak;
     }

     get profile(): UserProfile | undefined{
       return this._profile;
     }

      async init(){
         const  authenticated = await this.keycloak?.init({
         onLoad: 'login-required',
             });

         if(authenticated){
             //if user is authenticated
           this._profile = (await this.keycloak?.loadUserProfile()) as UserProfile;
           this._profile.token = this.keycloak?.token;
            }
       }

      login(){
         return this.keycloak?.login();
      }

     logout(){
         return this.keycloak?.logout({
            redirectUri: 'http://localhost:4200'
            });
      }

}

  //linked to the app.module.ts
  // when linking the agenda is to allow keycloak to run when I boostrap my app
