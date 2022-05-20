import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Fact } from './interfaces/Fact';
import { Type } from '../constants';

@Injectable({
  providedIn: 'root',
})
export class FactService {
  private factsUrl = 'api/facts';

  constructor(private http: HttpClient) {}

  addFact(fact: Fact): Observable<HttpResponse<Fact>> {
    return this.http.post<Fact>('api/facts/add', fact, {
      headers: { 'Content-Type': 'application/json' },
      observe: 'response',
    });
  }

  editFact(fact: Fact): Observable<HttpResponse<Fact>> {
    return this.http.post<Fact>('api/facts/edit', fact, {
      headers: { 'Content-Type': 'application/json' },
      observe: 'response',
    });
  }

  /** GET regions from the server */
  getFactsByRegion(
    regionId: string,
    type?: Type,
  ): Observable<HttpResponse<Fact[]>> {
    let url: string;
    if (type && type !== Type.ALL) {
      url = `${this.factsUrl}/${regionId}?type=${type}`;
    } else {
      url = `${this.factsUrl}/${regionId}`;
    }
    return this.http.get<Fact[]>(url, {
      headers: { 'Content-Type': 'application/json' },
      observe: 'response',
    });
  }

  getAllFacts(): Observable<Fact[]> {
    return this.http
      .get<Fact[]>(`${this.factsUrl}/all`)
      .pipe(catchError(this.handleError<Fact[]>('getAllFacts', [])));
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      // this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
