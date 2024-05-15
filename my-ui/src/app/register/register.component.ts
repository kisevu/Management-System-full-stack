import { Component } from '@angular/core';
import { SignUpRequest } from '../services/models/sign-up-request';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/services';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  signUpRequest: SignUpRequest = {email: '', password: '',lastname: '', firstname: ''};
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ){}

  register(){
    this.errorMsg = [];
    this.authService.signUp({
      body: this.signUpRequest
    }).subscribe({
      next: ()=>{
        this.router.navigate(['activate-account']);
      },
      error: (err)=>{
        this.errorMsg = err .error.validationErrors;
      }
    })
    ;
  }

  login(){
    this.router.navigate(['login'])
  }
}
