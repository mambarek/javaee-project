import {NgModule} from '@angular/core';
import {UserComponent} from './user.component';
import {UserStartComponent} from './user-start/user-start.component';
import {UserListComponent} from './user-list/user-list.component';
import {UserDetailComponent} from './user-detail/user-detail.component';
import {UserEditComponent} from './user-edit/user-edit.component';
import {UserRoutingModule} from './user-routing.module';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    UserComponent,
    UserStartComponent,
    UserListComponent,
    UserDetailComponent,
    UserEditComponent
  ],
  imports: [
    RouterModule,
    CommonModule,
    ReactiveFormsModule,
    UserRoutingModule]
})
export class UserModule {}
