import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/services/authentication.service';

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss'
})
export class ActivateAccountComponent {

  message: string= '';
  isOkay: boolean = true;
  isSubmitted: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ){}

  onCodeCompleted(token:string){
    this.confirmAccount(token);
  }

  redirectLogin(){
    this.router.navigate(['login']);
  }

  confirmAccount(token:string){
    this.authService.confirm({
      token
    }).subscribe({
      next: () => {
        this.message ='Your account has successfully been activated. \n Proceed to login';
        this.isSubmitted = true;
        this.isOkay = true;
      },
      error: () =>{
        this.message ='Token has expired or is invalid';
        this.isSubmitted = true;
        this.isOkay = false;
      }
    })
  }
}
