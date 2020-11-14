import {NgModule} from '@angular/core';
import {SignInComponent} from './signin/signin.component';
import {SignUpComponent} from './signup/signup.component';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';

@NgModule({
  declarations: [SignInComponent, SignUpComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild([
      {path: 'signin', component: SignUpComponent},
      {path: 'signup', component: SignInComponent}])
  ],
  exports: [RouterModule]
})
export class AuthModule {

}
