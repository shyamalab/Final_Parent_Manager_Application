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
  allUserList: any = [];
  submitstatus: string;
  modalBody: string;
  modalHeading: string;
  screenLoader: boolean = false;

  sortingName: string;
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
    backendService.getAllUserslist().subscribe((data: any) => {
      this.allUserList = data;
      this.screenLoader = false;
    });
  }

  ngOnInit() {
    this.loadUserslist();
    this.submitted = false;
    this.translate.get(['FirstNameLabel', 'LastNameLabel', 'EmployeeIdLabel'])
      .subscribe(translations => {
        this.firstNameLabel = translations['FirstNameLabel'];
        this.lastNameLabel = translations['LastNameLabel'];
        this.employeeIdLabel = translations['EmployeeIdLabel'];
      });
  }

  loadUserslist() {
    return this.backendService.getAllUserslist().subscribe((data: any) => {
      this.allUserList = data;
      this.screenLoader = true;
    });
  }

  onClickSortUser(event) {
    this.loadUserslist();
    var target = event.target || event.srcElement || event.currentTarget;
    var idAttr = target.attributes.id;
    var value = idAttr.nodeValue;
    this.allUserList = _.sortBy(this.allUserList, value.substring(0, value.length - 1));
  }

  adduser(data) {
    if (this.isEdit) {
      this.UpdateUser();
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
          this.loadUserslist();
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

  editUser(user: any) {
    this.screenLoader = true;
    this.User = user;
    this.usermodel = {
      firstName: user.firstName,
      lastName: user.lastName,
      employeeID: user.employeeID
    }
    this.isEdit = true;
  }

  UpdateUser() {
    this.backendService.updateUser(this.User.user_ID, this.usermodel)
      .subscribe((data: {}) => {
        this.router.navigate(['/adduser']);
        this.submitted = true;
        this.errormessage = "";
        this.modalHeading = 'User Status';
        this.modalBody = 'Updated User Details Successfully';
        document.getElementById("submitModalOpener").click();
        this.loadUserslist();
      },
        error => {
          this.errormessage = error;
          if (error = "409") {
            this.modalHeading = 'Failed To Add User';
            this.modalBody = 'Users already exist with same employee_id';
            document.getElementById("submitModalOpener").click();
          }
          this.submitted = false;
          this.isEdit = true;
          this.editUser(this.User);
        })
  }

  CancelUserScreen() {
    this.resetButton();
    this.isEdit = false;
  }

  deleteUser(user: any) {
    this.screenLoader = true;
    this.backendService.deleteUser(user.user_ID).subscribe(
      (data: any) => {
        this.screenLoader = false;
        this.modalHeading = "Success Message";
        this.modalBody = 'User Deleted Successfully';
        document.getElementById("submitModalOpener").click();
        this.loadUserslist();

      }
    );
  }

}
