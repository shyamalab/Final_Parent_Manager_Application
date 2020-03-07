import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { User } from './Users';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  updatetask: any = {};
  url = 'http://localhost:8082/api/';

  constructor(private _httpclient: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  //User Service - Add

  adduserService(data): Observable<User> {
    return this._httpclient.post<User>(this.url + "add/user", JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.ErrorHanlding)
      )
  }

  ErrorHanlding(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = error.status;
    }
    return throwError(errorMessage);
  }
}
