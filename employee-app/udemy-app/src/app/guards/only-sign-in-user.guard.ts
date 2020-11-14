import {Injectable, OnDestroy} from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {AuthService} from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class OnlySignInUserGuard implements CanActivate, OnDestroy {
  userLoggedIn = false;
  subscriptions: Subscription[];

  constructor(private authService: AuthService) {
    this.subscriptions = [];

    const subscr1 = this.authService.signInSuccess$.subscribe(data => {
      console.log('OnlySignInUserGuard User successfully sign in');
      this.userLoggedIn = true;
    }, error => {
      console.log('OnlySignInUserGuard User sign in fails', error);
      this.userLoggedIn = false;
    });

    const subscr2 = this.authService.signOut$.subscribe(data => {
      console.log('OnlySignInUserGuard User successfully sign out');
      this.userLoggedIn = false;
    });

    this.subscriptions.push(subscr1);
    this.subscriptions.push(subscr2);
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.userLoggedIn;
  }

  ngOnDestroy(): void {
    console.log('OnlySignInUserGuard would be destroyed!!!');
    this.subscriptions.forEach(desc => desc.unsubscribe());
  }
}
