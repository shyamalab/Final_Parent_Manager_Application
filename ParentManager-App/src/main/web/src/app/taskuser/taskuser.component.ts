import { Component, OnInit, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { BackendService } from "./../backend.service";
import { Router } from '@angular/router';
import { User } from './../Users';
import * as _ from 'underscore';


@Component({
  selector: 'app-taskuser',
  templateUrl: './taskuser.component.html',
  styleUrls: ['./taskuser.component.css']
})
export class TaskuserComponent implements OnInit {

  User: any;
  submitstatus: string;
  modalBody: string;
  modalHeading: string;
  screenLoader: boolean = false;

  isEdit: boolean = false;

  @Input() usermodel = {
    firstName: '',
    lastName: '',
    employeeID: ''
  }

  firstNameLabel: string;
  lastNameLabel: string;
  employeeIdLabel: string;
  employeeIDPattern = /^\d+$/;
  errormessage: string;

  submitted = false;
  constructor(private backendService: BackendService,
    public router: Router,
    private translate: TranslateService) {

  }

  ngOnInit() {
    this.submitted = false;
    this.translate.get(['FirstNameLabel', 'LastNameLabel', 'EmployeeIdLabel'])
      .subscribe(translations => {
        this.firstNameLabel = translations['FirstNameLabel'];
        this.lastNameLabel = translations['LastNameLabel'];
        this.employeeIdLabel = translations['EmployeeIdLabel'];
      });
  }


  adduser(data) {
    if (this.isEdit) {
      this.isEdit = false;
    } else {
      this.backendService.adduserService(this.usermodel)
        .subscribe((data: {}) => {
          this.router.navigate(['/adduser']);
          this.submitted = true;
          this.errormessage = "";
          this.modalHeading = 'User Status';
          this.modalBody = 'User Added Successfully';
          document.getElementById("submitModalOpener").click();
          this.usermodel = {
            firstName: '',
            lastName: '',
            employeeID: ''
          }
        },
          error => {
            this.errormessage = error;
            if (error = "409") {
              this.modalHeading = 'Failed To Add User';
              this.modalBody = 'Users already exist with same employee_id';
              document.getElementById("submitModalOpener").click();
            }
            this.submitted = false;
          })
  
    }
    this.usermodel = {
      firstName: '',
      lastName: '',
      employeeID: ''
    }
  }
  resetButton() {
    this.submitted = false;
    this.usermodel = {
      firstName: '',
      lastName: '',
      employeeID: ''
    }
  
  }
  
  CancelUserScreen() {
    this.resetButton();
    this.isEdit = false;
  }

}
