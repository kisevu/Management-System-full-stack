import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services/authentication.service';
@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.scss']
})

export class ActivateAccountComponent {

   message:string = '';
   isOkay:boolean = true;
   submitted:boolean = false;
    constructor(
      private router: Router,
      private authService: AuthenticationService
    ){}

    onCodeCompleted(token: string): void {
    this.confirmAccount(token);
      }

    redirectToLogin(){
   this.router.navigate(['login']);
       }

   private confirmAccount(token:string): void {
    this.authService.confirm({
     token
      }).subscribe({
       next: () : void  => {
              this.message = 'Your account has been successfully activated. \n Now proceed to login';
              this.submitted = true;
                  },
       error: (err) : void => {
              this.message = 'Oops! your token expired.';
              this.submitted = true;
              this.isOkay = false;
                 }
           })
    }
}
