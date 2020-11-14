import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {User} from '../model/app.model';
import {EMPTY, Observable, of} from 'rxjs';
import {UserService} from './user.service';
import {map, mergeMap, take, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserDataResolverService implements Resolve<User> {

  cachedUser: User;

  constructor(private userService: UserService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User> | Promise<User> | User {
    const id = route.paramMap.get('id');
    console.log('Resolver resolve call id is', id);

    if (!id) { return of(null); }

    // try to get the user from cache
    if (this.cachedUser && this.cachedUser.id === id) {
      console.log('ActivatedRouteSnapshot user hold from cache', this.cachedUser);
      return of(this.cachedUser);
      /*      return new Observable<User>(subscriber => {
              subscriber.next(this.cachedUser);
            });*/
    }

    /*return this.userService.fetchUser(id);*/

    return this.userService.fetchUser2(id).pipe(tap(user => {
      // cache the user
      this.cachedUser = user;
    }));
  }
}
