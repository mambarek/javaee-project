import {Component, OnInit} from '@angular/core';
import {User} from '../../model/app.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-start',
  templateUrl: './user-start.component.html',
  styleUrls: ['./user-start.component.css']
})
export class UserStartComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  createNewUser() {
    const user: User = new User('', '', '');
  }
}
