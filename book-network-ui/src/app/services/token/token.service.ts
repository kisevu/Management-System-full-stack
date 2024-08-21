import { Injectable } from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  set token(token: string){
   localStorage.setItem('token',token);
   }

  get token(){
  return localStorage.getItem('token') as string;
  }

   isTokenNotValid(){
      return !this.isTokenValid();
     }

     isTokenValid(){
      const token: string = this.token;
         if(!token){
            return false;
          }
        //token decoding using @auth0/angular-jwt
        const jwtHelper: JwtHelperService = new JwtHelperService();
        //check exp date
        const isTokenExpired: boolean = jwtHelper.isTokenExpired(token);
        if(isTokenExpired){
        localStorage.clear();
         return false;
         }
      //if token is still valid
       return true;
     }
}
