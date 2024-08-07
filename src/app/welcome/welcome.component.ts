import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css'],
  animations: [
    trigger('fadeInOut', [
      state('void', style({
        opacity: 0
      })),
      transition('void <=> *', [
        animate(500)
      ]),
    ]),
  ]
})
export class WelcomeComponent implements OnInit {
  currentSection: string = '';
  amount: number = 0;
  recipient: string = '';
  accountBalance: number = 0;
  userId: number = 1; // Replace with actual user ID logic

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.getAccountBalance();
  }

  getAccountBalance() {
    this.http.get<{ balance: number }>(`${environment.apiBaseUrl}/user/balance/${this.userId}`)
      .subscribe(
        response => {
          this.accountBalance = response.balance;
        },
        error => {
          if (error.status === 404) {
            console.error('User not found');
          } else {
            console.error('Failed to fetch account balance', error);
          }
        }
      );
  }

  showSection(section: string) {
    this.currentSection = section;
  }

  withdraw() {
    const body = { amount: this.amount };
    this.http.post(`${environment.apiBaseUrl}/withdraw`, body)
      .subscribe(response => {
        console.log('Withdraw successful', response);
        this.getAccountBalance(); // Refresh balance after transaction
      }, error => {
        console.error('Withdraw failed', error);
      });
  }

  deposit() {
    const body = { amount: this.amount };
    this.http.post(`${environment.apiBaseUrl}/deposit`, body)
      .subscribe(response => {
        console.log('Deposit successful', response);
        this.getAccountBalance(); // Refresh balance after transaction
      }, error => {
        console.error('Deposit failed', error);
      });
  }

  transfer() {
    const body = { amount: this.amount, recipient: this.recipient };
    this.http.post(`${environment.apiBaseUrl}/transfer`, body)
      .subscribe(response => {
        console.log('Transfer successful', response);
        this.getAccountBalance(); // Refresh balance after transaction
      }, error => {
        console.error('Transfer failed', error);
      });
  }
}
