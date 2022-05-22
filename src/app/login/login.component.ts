import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  credentials = { username: '', password: '' };

  constructor(
    private app: AppService,
    private http: HttpClient,
    private router: Router,
    private authService: AuthService,
  ) {}

  login() {
    this.authService.authenticate(this.credentials, () => {
      this.router.navigateByUrl('/');
    });
    return false;
  }

  ngOnInit(): void {
    this.app.toggleMainBackground();
  }
}
