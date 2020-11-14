import {Component, OnDestroy, OnInit} from '@angular/core';
import {User} from '../../model/app.model';
import {UserService} from '../../services/user.service';
import {ActivatedRoute, ActivatedRouteSnapshot} from '@angular/router';
import {error} from 'util';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit, OnDestroy {
  /* the UserDetailComponent may take the id from Router Url and try to hold
  * the origin user from backend. another approach ist to subscribe to the UserService
  * selectedUser event to become the user from service*/
  userId: number;
  user: User;
  subscriptions: Subscription[] = [];
  isFetchingData = true;

  constructor(private userService: UserService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    console.log('UserDetailsComponent::ngOnInit ---');
    this.isFetchingData = true;
    console.log('User service is fetching ', this.userService.isFetching);
    this.tryGetUserFromResolver();
  }

  tryGetUserFromResolver() {
    this.route.data
      .subscribe((data: {user: User}) => {
        this.user = data.user;
        this.isFetchingData = false;
      });
  }

  tryGetUserFromActiveRouter() {

    const subscr1 = this.route.paramMap.subscribe(params => {
      this.userId = +params.get('id');
      this.userService.getUser(this.userId).then(value => {
        console.log('User service is fetching ', this.userService.isFetching);
        this.user = value;
      }).catch(reason => {
        this.user = null;
      });
    });

    this.subscriptions.push(subscr1);
  }

  tryGetUserFromService() {

    this.userId = +this.route.snapshot.paramMap.get('id');
    this.userService.getUser(this.userId).then(value => {
      console.log('User service is fetching ', this.userService.isFetching);
      this.user = value;
    }).catch(reason => {
      this.user = null;
    });

    // listen to user select subject (new user selected) to update this view
    const subscr1 = this.userService.selectedUser$.subscribe(user => {
      this.route.paramMap.subscribe(params => {
        this.userId = +params.get('id');
        // get the user from backend not from the wrapper list
        this.userService.getUser(this.userId).then(value => {
          console.log('User service is fetching ', this.userService.isFetching);
          this.user = value;
        }).catch(reason => {
          this.user = null;
        });
      });
    });

    this.subscriptions.push(subscr1);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
}
