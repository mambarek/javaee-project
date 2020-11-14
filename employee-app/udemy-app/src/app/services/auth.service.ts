import {Injectable} from '@angular/core';
import {BehaviorSubject, Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import { environment} from '../../environments/environment';

class FirebaseCredential {
  email: string;
  password: string;
  returnSecureToken = true;

  constructor(email: string, password: string, returnSecureToken: boolean) {
    this.email = email;
    this.password = password;
    this.returnSecureToken = returnSecureToken;
  }
}

class FirebaseSignUpResponse {
  idToken: string; // A Firebase Auth ID token for the newly created user.
  email: string; // The email for the newly created user.
  refreshToken: string; // A Firebase Auth refresh token for the newly created user.
  expiresIn: string; // The number of seconds in which the ID token expires.
  localId;
  string; // The uid of the newly created user.
}

class FirebaseSignInResponse {
  idToken: string; // A Firebase Auth ID token for the newly created user.
  email: string; // The email for the newly created user.
  refreshToken: string; // A Firebase Auth refresh token for the newly created user.
  expiresIn: string; // The number of seconds in which the ID token expires.
  localId: string; // The uid of the newly created user.
  registered: string; // Whether the email is for an existing account.
}

@Injectable({providedIn: 'root'})
export class AuthService {

  signUpErrors = {
    EMAIL_EXISTS: 'The email address is already in use by another account.',
    OPERATION_NOT_ALLOWED: 'Password sign-in is disabled for this project.',
    TOO_MANY_ATTEMPTS_TRY_LATER: 'We have blocked all requests from this device due to unusual activity. Try again later.'
  };

  signInErrors = {
    EMAIL_NOT_FOUND: 'There is no user record corresponding to this identifier. The user may have been deleted.',
    INVALID_PASSWORD: 'The password is invalid or the user does not have a password.',
    USER_DISABLED: 'The user account has been disabled by an administrator.'
  };

  // sin up
  signUpSuccess$: Subject<FirebaseSignUpResponse>;
  signUpError$: Subject<any>;

  // sign out
  signOut$: Subject<any>;

  // sign in
  signInSuccess$: Subject<FirebaseSignInResponse>;
  signInError$: Subject<any>;

  singUpUrl = `https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=${environment.firebaseAPIKey}`;
  singInUrl = `https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${environment.firebaseAPIKey}`;

  constructor(private http: HttpClient) {
    this.signOut$ = new Subject();
    this.signInSuccess$ = new Subject();
    this.signInError$ = new Subject();
    this.signUpSuccess$ = new Subject<FirebaseSignUpResponse>();
    this.signUpError$ = new Subject<string>();
  }

  signUp(userCredential: FirebaseCredential) {
    this.http.post<FirebaseSignUpResponse>(this.singUpUrl, userCredential).subscribe(data => {
      console.log('User registration success for', userCredential, data);
      this.signUpSuccess$.next(data);
    }, error => {
      console.log('User registration Fails for', userCredential, error);
      this.signUpError$.next(error);
    });
  }

  signIn(userCredential: FirebaseCredential) {
    this.http.post<FirebaseSignInResponse>(this.singInUrl, userCredential).subscribe(data => {
      console.log('User registration success for', userCredential, data);
      this.signInSuccess$.next(data);
    }, error => {
      console.log('User registration Fails for', userCredential, error);
      this.signInError$.next(error);
    });
  }

  singOut() {
    this.signOut$.next(null);
  }
}
