import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Poi } from './interfaces/Poi';
import { Type } from '../constants';
import { Fact } from './interfaces/Fact';

@Injectable({
  providedIn: 'root',
})
export class PoiService {
  private poiUrl = 'api/poi';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private http: HttpClient) {}

  addPoi(poi: Poi): Observable<HttpResponse<Poi>> {
    return this.http.post<Poi>('api/poi/add', poi, {
      headers: { 'Content-Type': 'application/json' },
      observe: 'response',
    });
  }

  editPoi(poi: Poi): Observable<HttpResponse<Poi>> {
    return this.http.post<Poi>('api/poi/edit', poi, {
      headers: { 'Content-Type': 'application/json' },
      observe: 'response',
    });
  }

  deletePoiById(
    poiId: string,
  ): Observable<HttpResponse<{ id: string; deleted: boolean }>> {
    return this.http.post<{ id: string; deleted: boolean }>(
      'api/poi/delete',
      { id: poiId },
      {
        headers: { 'Content-Type': 'application/json' },
        observe: 'response',
      },
    );
  }

  /** GET regions from the server */
  getPoiByRegion(regionId: string, type?: Type): Observable<Poi[]> {
    let url: string;
    if (type && type !== Type.ALL) {
      url = `${this.poiUrl}/${regionId}?type=${type}`;
    } else {
      url = `${this.poiUrl}/${regionId}`;
    }
    return this.http
      .get<Poi[]>(url, this.httpOptions)
      .pipe(catchError(this.handleError<Poi[]>('getPoiByRegion', [])));
  }

  getAllPoi(): Observable<Poi[]> {
    return this.http
      .get<Poi[]>(`${this.poiUrl}/all`)
      .pipe(catchError(this.handleError<Poi[]>('getAllPoi', [])));
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
