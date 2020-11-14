import {Component, OnDestroy, OnInit} from '@angular/core';
import {User} from '../../model/app.model';
import {Subscription} from 'rxjs';
import {UserService} from '../../services/user.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit, OnDestroy {
  userList: User[];
  subscriptions: Subscription[];

  constructor(private userService: UserService, private router: Router, private route: ActivatedRoute) {
    this.userList = [];
    this.subscriptions = [];
  }

  ngOnInit() {
/*    const subs1 = this.userRegistrationService.registrationSuccess.subscribe(data => {
      this.userList.push(data.user);
    });*/
    const subs2 = this.userService.fetchSuccess$.subscribe(data => {
      this.userList = data;
    });

    const subs3 = this.userService.userListChanged$.subscribe(data => {
      console.log('Userlist changed event new list', data);
      this.userList = data;
    });

    // this.subscriptions.push(subs1);
    this.subscriptions.push(subs2);
    this.subscriptions.push(subs3);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subs => subs.unsubscribe());
  }

  editUser(index) {
    console.log('Selected user index: ', index);
    this.userService.selectUser(index);
    const user = this.userList[index];
    this.router.navigate([user.id], {relativeTo: this.route});
    // this.router.navigate([index], {relativeTo: this.route});
    // this.router.navigate(['/user', index]);
    // this.router.navigate(['/user' + index]);
  }

  dataLoading() {
    return this.userService.isFetching;
  }
}
