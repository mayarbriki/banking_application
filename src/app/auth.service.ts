import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from '../environments/environment';  // adjust the path as necessary
import { User } from './user';  // adjust the path as necessary

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient, private router: Router) {}

  public signIn(user: User): Observable<User | null> {
    console.log('Signing in with user:', user);
    return this.http.post<User>(`${this.apiServerUrl}/user/login`, user, { observe: 'response' }).pipe(
      map(response => {
        console.log('Received response:', response);
        if (response.status === 200 && response.body) {
          return response.body as User;
        } else {
          console.error('Unexpected response status:', response.status);
          return null;
        }
      }),
      catchError((error: HttpErrorResponse) => {
        console.error('Error occurred:', error);
        let errorMessage = 'An unknown error occurred!';
        if (error.error instanceof ErrorEvent) {
          errorMessage = `Error: ${error.error.message}`;
        } else {
          errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
        }
        return throwError(() => new Error(errorMessage));
      })
    );
  }
  public signUp(user: User): Observable<User> {     return this.http.post<User>(`${this.apiServerUrl}/user/signup`, user);   }
}
