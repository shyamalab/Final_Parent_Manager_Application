import { Component, OnInit, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { User } from './../Users';
import { BackendService } from "./../backend.service";
import { NgbDate, NgbCalendar, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { Project } from './../Project';
import * as _ from 'underscore';

@Component({
  selector: 'app-taskproject',
  templateUrl: './taskproject.component.html',
  styleUrls: ['./taskproject.component.css']
})
export class TaskprojectComponent implements OnInit {


  @Input() projectmodel = {
    project: '',
    start_Date: '',
    end_Date: '',
    priority: '0',
    projectstatus: false,
    taskcount: '',
    taskcompletion: '',
    users: ''
  }

  Project: any;
  allProjectList: any = [];
  allUserList: any = [];
  submitstatus: string;
  modalBody: string;
  modalHeading: string;
  screenLoader: boolean = false;
  chkEnable: boolean = false;
  dateEnable: boolean = false;
  setStartDate: string;
  setEndDate: string;

  calendarToday: NgbCalendar
  fromDate: NgbDate;
  toDate: NgbDate;

  //sortingProject: string;
  isEdit: boolean = false;
  selectedUser: User;

  projectLabel: string;
  start_DateLabel: string;
  end_DateLabel: string;
  errormessage: string;

  searchUser:string;

  submitted = false;
  constructor(private backendService: BackendService,
    public router: Router,
    private calendar: NgbCalendar,
    public formatter: NgbDateParserFormatter,
    private translate: TranslateService) {
    this.fromDate = calendar.getToday();
    this.toDate = calendar.getNext(calendar.getToday(), 'd', 1);
    backendService.getAllProjectlist().subscribe((data: any) => {
      this.allProjectList = data;
      this.screenLoader = false;
    });
    backendService.getAllUserslist().subscribe((data: any) => {
      this.allUserList = data;
      this.screenLoader = false;
    });
  }

  ngOnInit() {
    this.loadProjectlist();
    this.submitted = false;
    this.translate.get(['ProjectLabel', 'StartDateLabel', 'EndDateLabel'])
      .subscribe(translations => {
        this.projectLabel = translations['ProjectLabel'];
        this.start_DateLabel = translations['StartDateLabel'];
        this.end_DateLabel = translations['EndDateLabel'];
      });
  }

  loadProjectlist() {
    return this.backendService.getAllProjectlist().subscribe((data: any) => {
      this.allProjectList = data;
      this.screenLoader = true;
    });
  }

  enableProjectDate(values: boolean) {
    this.dateEnable = values;
    if (this.dateEnable) {
      if(!this.isEdit){
      this.projectmodel.start_Date = this.formatter.format(this.fromDate);
      this.projectmodel.end_Date = this.formatter.format(this.toDate);
    }
    } else {
      this.projectmodel.start_Date = "";
      this.projectmodel.end_Date = "";
    }
  }

  onSelectManager(user: any) {
    this.selectedUser = user;
    this.projectmodel.users = user;
  }
  onClickSortProject(event) {
    var target = event.target || event.srcElement || event.currentTarget;
    var idAttr = target.attributes.id;
    var value = idAttr.nodeValue;
    this.allProjectList = _.sortBy(this.allProjectList, value.substring(0, value.length - 1));
  }

  addproject(data) {
    if (this.isEdit) {
     this.UpdateProject();
     this.screenLoader = false;
      this.isEdit = false;
    } else {
      this.backendService.addprojectService(this.projectmodel)
        .subscribe((data: {}) => {
          this.router.navigate(['/addproject']);
          this.submitted = true;
          this.errormessage = "";
          this.modalHeading = 'Project Status';
          this.modalBody = 'Project Added Successfully';
          document.getElementById("submitModalOpener").click();
          this.loadProjectlist();
          this.projectmodel = {
            project: '',
            start_Date: '',
            end_Date: '',
            priority: '',
            projectstatus: false,
            taskcount: '',
            taskcompletion: '',
            users: ''
          }
          this.enableProjectDate(false);
        },
          error => {
            this.errormessage = error;
            if (error = "409") {
              this.modalHeading = 'Failed To Add Project';
              this.modalBody = 'Project already exist with same employee_id';
              document.getElementById("submitModalOpener").click();
            }
            this.submitted = false;
          })
    }
    this.enableProjectDate(false);
    this.searchUser ="";
    this.projectmodel = {
      project: '',
      start_Date: '',
      end_Date: '',
      priority: '0',
      projectstatus: false,
      taskcount: '',
      taskcompletion: '',
      users: ''
    }
  }
  resetButton() {
    this.submitted = false;
    this.enableProjectDate(false);
    this.searchUser="";
    this.projectmodel = {
      project: '',
      start_Date: '',
      end_Date: '',
      priority: '0',
      projectstatus: false,
      taskcount: '',
      taskcompletion: '',
      users: ''
    }
  }

  editProject(project: any) {
    this.screenLoader = true;
    this.Project = project;
    this.enableProjectDate(true);
    this.projectmodel = {
      project: project.project,
      start_Date: project.start_Date,
      end_Date: project.end_Date,
      priority: project.priority,
      projectstatus: false,
      taskcount: project.taskcount,
      taskcompletion: project.taskcompletion,
      users: project.users
    }
    this.isEdit = true;
  }

  suspendProject(project: any){
    this.screenLoader = true;
    project.projectstatus = true;
    this.Project = project;
    this.backendService.updateProject(this.Project.projectID, this.Project)
    .subscribe((data: {}) => {
      this.router.navigate(['/addproject']);
      this.submitted = true;
      this.errormessage = "";
      this.modalHeading = 'Project Status';
      this.modalBody = 'Suspended Project Successfully';
      document.getElementById("submitModalOpener").click();
      this.loadProjectlist();
    },
      error => {
        this.errormessage = error;
        this.submitted = false;
        this.isEdit = true;
        this.editProject(this.Project);
      })
  }

  UpdateProject() {
    this.backendService.updateProject(this.Project.projectID, this.projectmodel)
      .subscribe((data: {}) => {
        this.router.navigate(['/addproject']);
        this.submitted = true;
        this.errormessage = "";
        this.modalHeading = 'Project Status';
        this.modalBody = 'Updated Project Details Successfully';
        document.getElementById("submitModalOpener").click();
        this.loadProjectlist();
      },
        error => {
          this.errormessage = error;
          this.submitted = false;
          this.isEdit = true;
          this.editProject(this.Project);
        })
        this.searchUser="";
  }

  CancelProjectScreen() {
    this.resetButton();
    this.isEdit = false;
  }

  error:any={isError:false,errorMessage:''};

compareTwoDates(){
   if(new Date(this.projectmodel.start_Date)>new Date(this.projectmodel.end_Date)){
      this.error={isError:true,errorMessage:"Start Date can't after End date"};
   } else {
    this.error={isError:false,errorMessage:""};
   }
}

}
