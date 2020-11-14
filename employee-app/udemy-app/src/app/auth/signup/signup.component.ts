import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {OverlayService} from '../../shared/overlay/overlay.service';

@Component({
  selector: 'app-registration',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignUpComponent implements OnInit {
  signUpError: any;

  subscriptions: Subscription[];

  constructor(private authService: AuthService, private router: Router, private overlayService: OverlayService ) {
    this.subscriptions = [];
  }

  ngOnInit() {
    const subscr1 = this.authService.signUpSuccess$.subscribe(firebaseSignUpResponse => {
      console.log('firebaseSignUpResponse', firebaseSignUpResponse);
      this.router.navigate(['']);
    });

    const subscr2 = this.authService.signUpError$.subscribe(error => {
      console.log('SignUp error', error);
      let message = error.error.error.message;
      if (message.indexOf(':') >= 0) {
        message = message.substr(message.indexOf(':') + 1, message.length);
      } else {
        if (this.authService.signUpErrors[message]) {
          message = this.authService.signUpErrors[message];
        }
      }
      console.log('SignUp error message: ', message);
      this.signUpError = message;
      this.overlayService.showOverlay(message);
    });
    this.subscriptions.push(subscr1);
  }

  onSubmit(value: any) {
    const firebaseCredential = {
      email: value.email,
      password: value.password,
      returnSecureToken: true
    };
    this.authService.signUp(firebaseCredential);
  }
}
