import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RegistrationRequest } from '../dto/registration-request';
import { RegistrationService } from '../registration.service';
import { ResponseData } from '../dto/response-data';
import { Router } from '@angular/router';
import { AppService } from '../app.service';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  visible = false;
  buttonRegister = true;
  registrationForm: FormGroup;
  submitted = false;

  private registrationData?: RegistrationRequest;

  constructor(
    public registrationClient: RegistrationService,
    private router: Router,
    private app: AppService,
  ) {
    this.registrationForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      confirmPassword: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
    });
  }

  enableForm(enable: boolean) {
    if (enable) {
      this.registrationForm.get('username')?.enable();
      this.registrationForm.get('password')?.enable();
      this.registrationForm.get('confirmPassword')?.enable();
      this.registrationForm.get('email')?.enable();
      this.buttonRegister = true;
    } else {
      this.registrationForm.get('username')?.disable();
      this.registrationForm.get('password')?.disable();
      this.registrationForm.get('confirmPassword')?.disable();
      this.registrationForm.get('email')?.disable();
      this.buttonRegister = false;
    }
  }

  async register() {
    this.submitted = true;
    if (this.registrationForm.invalid) {
      return;
    } else {
      this.registrationData = new RegistrationRequest();
      this.registrationData.setUsername(
        this.registrationForm.get('username')?.value,
      );
      this.registrationData.setPassword(
        this.registrationForm.get('password')?.value,
      );
      this.registrationData.setEmail(this.registrationForm.get('email')?.value);

      this.registrationClient
        .register(this.registrationData)
        .subscribe((data: ResponseData) => {
          if (data.responseCode == '200') {
            this.router.navigateByUrl('/login');
            // this.visible = true;
            this.enableForm(false);
          } else {
            this.enableForm(true);
          }
        }),
        (error: string) => {
          console.log('An Error Occurred ' + error);
        };
    }
  }

  ngOnInit(): void {
    this.app.toggleMainBackground();
  }
}
