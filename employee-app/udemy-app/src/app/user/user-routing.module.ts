import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserComponent} from './user.component';
import {UserStartComponent} from './user-start/user-start.component';
import {UserEditComponent} from './user-edit/user-edit.component';
import {UserDetailComponent} from './user-detail/user-detail.component';
import {UserDataResolverService} from '../services/user-data-resolver.service';
import {InterruptEditingGuard} from './user-edit/interrupt.editing.guard';
import {PageNotFoundComponent} from '../page-not-found/page-not-found.component';

const routes: Routes = [
  /*{path: 'user', component: UserComponent, canActivate: [OnlySignInUserGuard], children: [*/
  {
    path: '', component: UserComponent, children: [
      {path: '', component: UserStartComponent},
      {path: 'new', component: UserEditComponent},
      {path: ':id', component: UserDetailComponent, resolve: {user: UserDataResolverService}},
      {
        path: ':id/edit', component: UserEditComponent,
        canDeactivate: [InterruptEditingGuard], resolve: {user: UserDataResolverService}
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule {

}
