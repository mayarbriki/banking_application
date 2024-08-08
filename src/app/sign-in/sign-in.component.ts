import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { User } from '../user';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http'; // Import HttpClient
import { map, catchError } from 'rxjs/operators';
import {  throwError } from 'rxjs';
import { Router } from '@angular/router';
@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {
  user: User = {
    username: '',
    password: '' ,
    id: 0,
     email: '' ,
     full_name: ''
  };
  apiServerUrl = environment.apiBaseUrl; // Add this line if needed
  constructor(private authService: AuthService, private http: HttpClient,private router: Router) {}
  signIn() {
    console.log('Submitting form with user:', this.user);
    this.authService.signIn(this.user).subscribe(
      response => {
        if (response) {
          console.log('Sign-in successful:', response);
          this.router.navigate(['/welcome']);
        } else {
          console.error('Sign-in failed');
        }
      },
      error => {
        console.error('Error during sign-in:', error.message);
      }
    );
  }
  
}
