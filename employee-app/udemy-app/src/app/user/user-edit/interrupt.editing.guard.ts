import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot, UrlTree} from '@angular/router';
import {UserEditComponent} from './user-edit.component';
import {Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class InterruptEditingGuard implements CanDeactivate<UserEditComponent> {
  canDeactivate(component: UserEditComponent, currentRoute: ActivatedRouteSnapshot,
                currentState: RouterStateSnapshot,
                nextState?: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return component.canDeactivate() || window.confirm('Do you want to cancel editing');
  }

}
