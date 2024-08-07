import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { User } from '../user';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {
  user: User = {
    username: '',
    password: '' ,
    id: 0,
     email: '' ,
     full_name: ''
  };

  constructor(private authService: AuthService) { }

  signUp() {
    this.authService.signUp(this.user).subscribe(
      response => {
        console.log('User signed up successfully', response);
      },
      error => {
        console.error('Error signing up user', error);
      }
    );
  }
}
