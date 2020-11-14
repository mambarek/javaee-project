import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {OverlayService} from '../../shared/overlay/overlay.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {

  userLoggedIn = false;
  subscriptions: Subscription[];

  constructor(private authService: AuthService, private router: Router, private overlayService: OverlayService) {
    this.subscriptions = new Array<Subscription>();
  }

  ngOnInit() {
    this.authService.signUpSuccess$.subscribe(value => {
      console.log('User successfully sign up');
      this.userLoggedIn = true;
    });

    const subscr1 = this.authService.signInSuccess$.subscribe(data => {
      console.log('User successfully sign in');
      this.userLoggedIn = true;
    }, error => {
      console.log('User sign in fails', error);
      this.userLoggedIn = false;
    });

    const subscr2 = this.authService.signInError$.subscribe(data => {
      this.userLoggedIn = false;
    });

    this.subscriptions.push(subscr1);
    this.subscriptions.push(subscr2);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(desc => desc.unsubscribe());
  }

  signOut() {
    this.userLoggedIn = false;
    this.router.navigate(['signin']);
    this.authService.singOut();
  }

  showOverlay() {
    this.overlayService.showOverlay('Hello! this is a message from Header.');
  }
}
