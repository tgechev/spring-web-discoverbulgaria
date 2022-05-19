import { Injectable } from '@angular/core';
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
} from '@angular/common/http';
import { ResponseData } from './dto/response-data';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { ResponseError } from './dto/response-error';
import { RegistrationRequest } from './dto/registration-request';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root',
})
export class RegistrationService {
  // baseUrl = 'http://localhost:8080';
  registrationUrl = '/register';

  constructor(private http: HttpClient) {}

  register(
    requestRegistrationDTO: RegistrationRequest,
  ): Observable<ResponseData> {
    return this.http
      .post<ResponseData>(
        this.registrationUrl,
        requestRegistrationDTO,
        httpOptions,
      )
      .pipe(retry(1), catchError(this.handleError));
  }

  handleError(error: { status: any; error: { error: any; message: any } }) {
    let errorMessage = `Error Code: ${error.status} - ${error.error.error} \nMessage: ${error.error.message}`;
    window.alert(errorMessage);
    return throwError(() => errorMessage);
  }
}
