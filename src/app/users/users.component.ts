import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import {
  HttpClient,
  HttpErrorResponse,
  HttpResponse,
} from '@angular/common/http';
import { UserService } from '../user.service';
import { User } from '../interfaces/User';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit, AfterViewInit {
  constructor(
    private app: AppService,
    private userService: UserService,
    private http: HttpClient,
  ) {}

  selectedUser: User = { id: '', username: '', admin: false };

  users: User[] = [];

  ngOnInit(): void {
    this.app.toggleMainBackground();
  }

  ngAfterViewInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getUsers().subscribe(users => (this.users = users));
  }

  onSubmit(): void {
    this.http
      .post<HttpResponse<User>>('api/users/edit', this.selectedUser, {
        headers: { 'Content-Type': 'application/json' },
        observe: 'response',
      })
      .subscribe({
        next: response => {
          if (response.status == 200) {
            console.log(`success edit user`);
          }
        },
        error: (error: HttpErrorResponse) => {
          console.log('An Error Occurred ' + error.message);
        },
      });
  }
}
