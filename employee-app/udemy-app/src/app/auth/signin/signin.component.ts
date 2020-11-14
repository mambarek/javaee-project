import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {NgForm} from '@angular/forms';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SignInComponent implements OnInit, OnDestroy {
  @ViewChild('signInFormRef', {static: false}) signInForm: NgForm;

  subscriptions: Subscription[];
  signInError: string;

  constructor(private authService: AuthService, private router: Router) {
    this.subscriptions = [];
  }

  ngOnInit() {
    console.log('+++++ Router', this.router);
    const subscr1 = this.authService.signInSuccess$.subscribe(data => {
      this.router.navigate(['']);
    });

    const subscr2 = this.authService.signInError$.subscribe(error => {
      console.log('SignIn error', error);
      const messageId = error.error.error.message;
      console.log('SignIn error message: ', this.authService.signInErrors[messageId]);
      this.signInError = this.authService.signInErrors[messageId];
    });
    this.subscriptions.push(subscr1);
    this.subscriptions.push(subscr2);
  }

  onSubmit(value) {
    this.signInError = null;
    // console.log('SigInForm', this.signInForm.form);
    console.log('SigInForm value', value);
    this.authService.signIn({
      email: value.email,
      password: value.password,
      returnSecureToken: true
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
}
