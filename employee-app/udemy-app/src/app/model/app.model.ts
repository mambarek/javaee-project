export class UserCredential {
  login: string;
  password: string;
  // token: string;

  constructor(login: string, pwd: string) {
    this.login = login;
    this.password = pwd;
  }
}

export class User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;

  constructor(firstName: string, lastName: string, email: string) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }
}
