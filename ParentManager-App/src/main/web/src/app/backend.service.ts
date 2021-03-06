import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { User } from './Users';
import { Project } from './Project';
import { Task } from './Task';

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

  //User Service - DeleteUser

  deleteUser(id): Observable<any> {
    return this._httpclient.delete<any>(this.url + "delete/user/" + id, this.httpOptions)
      .pipe(
        retry(1),
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

  //Task Service - Add

  addtaskService(data): Observable<Task> {
    return this._httpclient.post<Task>(this.url + "add/task", JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.ErrorHanlding)
      )
  }

  //Task Service - Get All
  getAllTasklist(): Observable<any> {
    return this._httpclient.get(this.url + "all/tasks", this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.ErrorHanlding)
      )
  }

  //Task Service - Get All active
  getAllTasklistwithoutCurrent(id): Observable<any> {
    return this._httpclient.get(this.url + "all/tasks/current/" + id, this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.ErrorHanlding)
      )
  }

  //Task Service - Get All Parent

  getAllParentTasklist(): Observable<any> {
    return this._httpclient.get(this.url + "all/parenttasks", this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.ErrorHanlding)
      )
  }

  //Task Service - Update
  edittask(id, data): Observable<Task> {
    return this._httpclient.put<Task>(this.url + "/tasks/" + id, JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.ErrorHanlding)
      )
  }

  //Task Service - Get By ID
  gettaskbyID(id): Observable<Task> {
    return this._httpclient.get<Task>(this.url + 'get/task/' + id, this.httpOptions)
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
