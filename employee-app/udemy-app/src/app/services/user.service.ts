import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {BehaviorSubject, EMPTY, Observable, of, Subject, Subscription} from 'rxjs';
import {User} from '../model/app.model';
import {delay, mergeMap, take} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class UserService {
  // user creation
  userCreationSuccess$: Subject<User>;
  userCreationFail$: Subject<string>;

  // user creation
  userUpdateSuccess$: Subject<User>;
  userUpdateFail$: Subject<string>;

  // delete user
  deleteUserSuccess$: Subject<string>;
  deleteUserFails$: Subject<string>;

  // user fetch
  fetchSuccess$: Subject<User[]>;
  fetchFails$: Subject<string>;
  isFetching = false;

  userList: User[];
  userListChanged$: Subject<User[]>;
  selectedUser$: Subject<User>;
  subscriptions = new Array<Subscription>();

  userRepoBaseUrl = 'https://angular-test-7e5f3.firebaseio.com/user';

  constructor(private http: HttpClient) {
    this.userList = [];
    this.userListChanged$ = new Subject();
    this.selectedUser$ = new Subject();
    this.userCreationSuccess$ = new Subject();
    this.userCreationFail$ = new Subject<string>();
    this.userUpdateSuccess$ = new Subject();
    this.userUpdateFail$ = new Subject<string>();
    this.deleteUserSuccess$ = new Subject<string>();
    this.deleteUserFails$ = new Subject<string>();
    this.fetchSuccess$ = new Subject();
    this.fetchFails$ = new Subject<string>();
  }

  private createUser(user: User) {
    this.http.post<{ name: string }>(`${this.userRepoBaseUrl}.json`, user).subscribe(successData => {
      console.log('UserService::createUser success', successData);
      user.id = successData.name;
      this.userCreationSuccess$.next(user);
      this.userList.push(user);
      this.userListChanged$.next(this.userList.slice()); // sen a copy of the list
    }, error => {
      console.log('UserService::createUser fail', error);
      this.userCreationFail$.next('User creation failed!');
    });
  }

  private updateUser(user: User) {
    this.http.put(`${this.userRepoBaseUrl}/${user.id}.json`, user).subscribe(data => {
      console.log('User successfully updated!!!');
      this.userUpdateSuccess$.next(user);
      const userIndex = this.userList.findIndex((value) => {
        return value.id === user.id;
      });
      console.log('++ updated index: ', userIndex);
      this.userList[userIndex] = user;
      this.userListChanged$.next(this.userList.slice()); // sen a copy of the list
    }, error => {
      console.log('UserService::updateUser fail', error);
      this.userUpdateFail$.next('User update failed!');
    });
  }

  persistUser(user: User) {
    if (!user) {
      return;
    }
    if (!user.id) {
      this.createUser(user);
    } else {
      this.updateUser(user);
    }
  }

  deleteUser(user: User) {
    const url = `${this.userRepoBaseUrl}/${user.id}.json`;
    this.http.delete<any>(url).subscribe(success => {
      this.deleteUserSuccess$.next(success);
      const newList = [];
      this.userList.forEach(u => {
        if (u.id !== user.id) {
          newList.push(u);
        }
      });
      // this.userList.slice(this.userList.indexOf(foundUser), 1);
      this.userListChanged$.next(newList); // send a copy of the list
    }, error => {
      this.deleteUserFails$.next(error);
    });
  }

  // echt geil wie man aus einem Observable mit pipe werte holt ohn subscription
  fetchUser(id: string): Observable<User> | Observable<never> {
    const url = this.userRepoBaseUrl + '/' + id + '.json';

    return this.http.get<any>(url).pipe(
      delay(2000), // TODO; for testing only delay 2 seconds to see th UI
      take(1),
      mergeMap(user => {
        console.log('-- fetchUser user', user);
        if (user) {
          return of(user);
        } else { // id not found
          return EMPTY;
        }
      })
    );
  }

  fetchUser2(id: string): Observable<User> {
    const url = `${this.userRepoBaseUrl}/${id}.json`;

    return this.http.get<any>(url).pipe(
      delay(5000), // TODO; for testing only delay 2 seconds to see th UI
      take(1),
      mergeMap(user => {
        console.log('-- fetchUser user', user);
        if (user) {
          user.id = id;
          return of(user);
        } else { // id not found
          return EMPTY;
        }
      })
    );
  }

  fetchAllUser() {
    this.isFetching = true;

    this.http.get<User[]>(`${this.userRepoBaseUrl}.json`).subscribe(successData => {
      console.log('Fetched users', successData);
      this.userList = [];
      for (const userId in successData) {
        if (successData.hasOwnProperty(userId)) {
          const user = successData[userId];
          user.id = userId;
          this.userList.push(user);
        }
      }

      setTimeout(() => {
        const listCopy = this.userList.slice();
        this.fetchSuccess$.next(listCopy);
        this.userListChanged$.next(listCopy); // sen a copy of the list
        this.isFetching = false;
      }, 2000);

    }, errorData => {
      console.log('Fetching user an error occurred', errorData);
      this.fetchFails$.next(errorData);
    });
  }

  selectUser(index: number) {

    if (index < 0 || index >= this.userList.length) {

      return;
    }
    // we may hold the user from backend. its common that the list objects
    // are only wrapper with fiew attributes and that the origin user can be a complex object
    this.selectedUser$.next(this.userList[index]);
  }

  getUser(index: number): Promise<User> {
    const promise = new Promise<User>((resolve, reject) => {
      if (!this.userList || index < 0 || index >= this.userList.length) {
        // reject('User not yet found');
      } else {
        // we may hold the user from backend. its common that the list objects
        // are only wrapper with fiew attributes and that the origin user can be a complex object
        resolve(this.userList[index]);
      }
    });

    return promise;
  }

}
