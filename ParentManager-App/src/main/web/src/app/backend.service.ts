import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { User } from './Users';
import { Project } from './Project';

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

  //User Service - GetAll

  getAllUserslist(): Observable<any> {
    return this._httpclient.get(this.url + "all/users", this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.ErrorHanlding)
      )
  }


  //User Service - Update User

  updateUser(id, data): Observable<User> {
    return this._httpclient.put<User>(this.url + "users/" + id, JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.ErrorHanlding)
      )
  }

 //Project Service - Add

 addprojectService(data): Observable<Project> {
  return this._httpclient.post<Project>(this.url + "add/project", JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(2),
      catchError(this.ErrorHanlding)
    )
}

//Project Service - GetAll
getAllProjectlist(): Observable<any> {
  return this._httpclient.get(this.url + "all/projects", this.httpOptions)
    .pipe(
      retry(2),
      catchError(this.ErrorHanlding)
    )
}

//Project Service - GetAll - Active
getAllActiveProjectlist(): Observable<any> {
  return this._httpclient.get(this.url + "all/projects/active", this.httpOptions)
    .pipe(
      retry(2),
      catchError(this.ErrorHanlding)
    )
}

//Project Service - Update

updateProject(id, data): Observable<Project> {
  return this._httpclient.put<Project>(this.url + "project/" + id, JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
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
