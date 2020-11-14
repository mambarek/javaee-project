import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {WelcomeComponent} from './welcome/welcome.component';
import {AnimationComponent} from './animation/animation.component';

const routes: Routes = [
  {path: '', component: WelcomeComponent, pathMatch: 'full'},
  { path: 'animate', component: AnimationComponent},
  {path: 'user', loadChildren: () => import('./user/user.module').then(m => m.UserModule)}, // Angular 8 syntax
  /*
  Please note, that you need to ensure that in your tsconfig.json file, you use

"module": "esnext",
instead of

"module": "es2015",
Why would you use this syntax? In the future, it'll replace the "string-only" approach (i.e. the first alternative mentioned here).
It also will give you better IDE support.
   */
  // {path: 'user', loadChildren: './user/user.module#UserModule'}, // Lazy load module so remove it from imports in appModule
  {path: '**', component: PageNotFoundComponent}
  /*  ,
    {path: '**', component: PageNotFoundComponent} das habe ich nach dem splitten von userModule nach userModuleRouting
    verschieben müssen. Problem sonst wenn ich auf user menüpunt klicke kommt 'Page not found'
    Problem user routing wird nach '**' hinzugefügt. da bricht der router schon bei '**' ab und meldet was danach kommt als not found
     '**' muss immer zum Schluß kommen. ambesten in ein PageNotFoundRouterModle definiren und zum Schluß hinzufügen*/
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
