import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, NgForm, Validators} from '@angular/forms';
import {Observable, Subscription} from 'rxjs';
import {UserService} from '../../services/user.service';
import {User} from '../../model/app.model';
import {ActivatedRoute, Router} from '@angular/router';
import {consoleTestResultHandler} from 'tslint/lib/test';

class ViewModel {
  firstName: string;
  lastName: string;
  email: string;
}

@Component({
  selector: 'app-user-details',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit, OnDestroy {
  // @ViewChild('f', { static: false }) userForm: NgForm;
  userForm: FormGroup;
  subscriptions: Subscription[];
  creationMessage: string;
  creationSuccess;

  // model
  userId: number;
  user: User;
  model: ViewModel;

  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router) {
    this.subscriptions = [];
  }

  ngOnInit() {
    console.log('UserEditComponent::ngOnInit ---');
    this.subscribeToServices();
    this.subscriptions = [];
    this.creationSuccess = null;

    this.userForm = new FormGroup({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      email: new FormControl('')
    });

    this.tryGetUserFromResolver();
  }

  tryGetUserFromResolver() {
    if (this.route.snapshot.routeConfig.path === 'new') {
      this.user = new User('test', '', '');
      this.mapModelToView();
      return;
    }

    this.route.data
      .subscribe((data: { user: User }) => {
        this.fillNewUserDate(data.user);
      });
  }

  fillNewUserDate(data) {
    this.onReset();
    if (data && data.hasOwnProperty('id')) {
      this.user = data;
      // this.userForm.setValue(this.user);
      this.mapModelToView();
    }
    // this.userForm.patchValue(this.user);

  }

  subscribeToServices() {
/*    this.route.paramMap.subscribe(params => {
      this.userId = +params.get('id');
      this.userService.getUser(this.userId).then(data => {
        this.fillNewUserDate(data);
      });
    });*/

    // den benutzer von der liste holen wenn das fetching fertig ist
    // dies passiert nur einmal nachdem fetch stattgefunden hat
    // problem hier dass der router kommt zu früh before die user
    // geladen worden
/*    const subscr2 = this.userService.fetchSuccess$.subscribe(data => {
      this.userService.getUser(this.userId).then(value => {
        console.log('User service is fetching ', this.userService.isFetching);
        this.user = value;
        this.fillNewUserDate(value);
      }).catch(reason => {
        this.user = null;
      });
    });*/

/*    const subscription1 = this.userService.selectedUser$.subscribe(data => {
      console.log('SelectUser subject in Details view user', data);
      this.onReset();
      this.fillNewUserDate(data);
    });*/

    const subscription2 = this.userService.userCreationSuccess$.subscribe(data => {
      console.log('Creation success data', data);
      this.creationMessage = 'User was successfully created!';
      this.creationSuccess = true;

    });

    const subscription3 = this.userService.userCreationFail$.subscribe(data => {
      this.creationMessage = data;
      this.creationSuccess = false;
    });

    const subscription4 = this.userService.userUpdateSuccess$.subscribe(data => {
      // route to previews page or something else
      this.creationMessage = 'User was successfully updated!';
      this.creationSuccess = true;
    });
    const subscription5 = this.userService.deleteUserSuccess$.subscribe(data => {
      this.router.navigate(['user']);
    }, error => {
      console.log('Error while deleting user!!!!!!', this.user);
    });

    this.subscriptions.push(subscription2);
    this.subscriptions.push(subscription3);
    this.subscriptions.push(subscription4);
    this.subscriptions.push(subscription5);
  }

  onSubmit() {
    console.log('Form value', this.userForm.value);
    this.mapViewToModel();
    console.log('Form user', this.user);
    this.saveUser();
  }

  mapModelToView() {
    // Object.assign(this.user, values);
    this.model = new ViewModel();
    if (this.user) {
      this.model.firstName = this.user.firstName;
      this.model.lastName = this.user.lastName;
      this.model.email = this.user.email;
    }
    // this.userForm.setValue(this.model);
    this.userForm.patchValue(this.model);
  }

  mapViewToModel() {
    Object.assign(this.user, this.userForm.value);
  }


  saveUser() {
    // console.log(this.userForm.value);
    this.creationSuccess = null;
    this.userService.persistUser(this.user);
  }

  deleteUser() {
    this.userService.deleteUser(this.user);
  }

  onReset() {
    this.userForm.reset();
    this.creationSuccess = null;
    this.creationMessage = null;
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  onDelete() {
    this.deleteUser();
  }

  canDeactivate() {
    return this.userForm.valid;
  }

  goBack() {
    // Relative navigation back to the user
    // this.router.navigate(['../', { id: this.userId }], { relativeTo: this.route });
    this.router.navigate(['../'], {relativeTo: this.route});
  }
}
