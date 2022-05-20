import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
  HttpResponse,
} from '@angular/common/http';
import { UserService } from '../user.service';
import { EditUserDTO } from '../dto/edit-user';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  constructor(
    private app: AppService,
    private userService: UserService,
    private http: HttpClient,
  ) {}

  selectedUser: EditUserDTO = { id: '', username: '', admin: false };

  users: EditUserDTO[] = [];

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
      .post<HttpResponse<EditUserDTO>>(
        'api/users/edit',
        this.selectedUser,
        httpOptions,
      )
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
