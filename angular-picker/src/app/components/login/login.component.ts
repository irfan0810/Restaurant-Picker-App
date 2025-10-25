import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/common/user';
import { HeaderType } from 'src/app/enum/header-type.enum';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  public showLoading: boolean;
  private subscriptions: Subscription[] = [];
  
  constructor(private router: Router, 
    private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    if (this.authenticationService.isUserLoggedIn()) {
      this.router.navigateByUrl('/sessions');
    } else {
      this.router.navigateByUrl('/login');
    }
  }

  onLogin(user: User) {
    this.showLoading = true;
    this.subscriptions.push(
      this.authenticationService.login(user).subscribe(
        (response: HttpResponse<User>) => {
          const token = response.headers.get(HeaderType.JWT_TOKEN);

          this.authenticationService.saveToken(token);
          this.authenticationService.addUserToLocalCache(response.body);
          
          this.router.navigateByUrl('/sessions');
          this.showLoading = false;
        },
        (errorResponse: HttpErrorResponse) => {
          this.showLoading = false;

          if (errorResponse.status === 401) {
            alert('Login failed: Invalid username or password.');
          } else if (errorResponse.status === 0) {
            alert('Unable to connect to server. Please try again later.');
          } else {
            alert(`Login failed: ${errorResponse.error.message || 'Unexpected error occurred.'}`);
          }
        }
      )
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
