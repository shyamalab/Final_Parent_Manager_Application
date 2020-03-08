import { Component, OnInit, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { User } from './../Users';
import { BackendService } from "./../backend.service";
import { NgbDate, NgbCalendar, NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import { Router, ActivatedRoute } from '@angular/router';
import { Project } from './../Project';
import { ParentTask } from './../parent-task';
import { Task } from './../Task';
import * as _ from 'underscore';

@Component({
  selector: 'app-addtask',
  templateUrl: './addtask.component.html',
  styleUrls: ['./addtask.component.css']
})
export class AddtaskComponent implements OnInit {

  @Input() taskmodel = {
    task: '',
    isparent: false,
    start_Date: '',
    end_Date: '',
    priority: '0',
    endTask: false,
    parenTask: '',
    parent_Task: ParentTask,
    project: Project,
    users: User
  }

  Project: any;
  User: any;
  ParentTask: any;
  Task: any;
  allProjectList: any = [];
  allParentTaskList: any = [];
  allUserList: any = [];
  allTaskList: any = [];
  submitstatus: string;
  modalBody: string;
  modalHeading: string;
  modelstatus: boolean;
  screenLoader: boolean = false;
  chkParentEnable: boolean = false;
  setStartDate: string;
  setEndDate: string;

  id = this.actRoute.snapshot.params['id'];

  calendarToday: NgbCalendar
  fromDate: NgbDate;
  toDate: NgbDate;

  //sortingProject: string;
  isEdit: boolean = false;
  selectedUser: User;
  selectedProject: Project;
  selectParentTask: ParentTask;

  taskLabel: string;
  start_DateLabel: string;
  end_DateLabel: string;
  errormessage: string;

  submitted = false;

  constructor(private backendService: BackendService,
    public actRoute: ActivatedRoute,
    public router: Router,
    private calendar: NgbCalendar,
    public formatter: NgbDateParserFormatter,
    private translate: TranslateService) {
    this.fromDate = calendar.getToday();
    this.toDate = calendar.getNext(calendar.getToday(), 'd', 1);
    this.taskmodel.start_Date = this.formatter.format(this.fromDate);
    this.taskmodel.end_Date = this.formatter.format(this.toDate);
    backendService.getAllActiveProjectlist().subscribe((data: any) => {
      this.allProjectList = data;
      this.screenLoader = false;
    });
    backendService.getAllUserslist().subscribe((data: any) => {
      this.allUserList = data;
      this.screenLoader = false;
    });
    backendService.getAllParentTasklist().subscribe((data: any) => {
      this.allParentTaskList = data;
      this.screenLoader = false;
    });
    backendService.getAllTasklist().subscribe((data: any) => {
      this.allTaskList = data;
      this.screenLoader = false;
    });
    if (this.id != null) {
      backendService.gettaskbyID(this.id).subscribe((data: any) => {
        this.taskmodel = data;
        this.screenLoader = false;
        this.isEdit = true;
      })
      backendService.getAllTasklistwithoutCurrent(this.id).subscribe((data: any) => {
        this.allTaskList = data;
        console.log('all', data)
        this.screenLoader = false;
      });
    }

  }

  ngOnInit() {
    this.submitted = false;
    this.translate.get(['TaskLabel', 'StartDateLabel', 'EndDateLabel'])
      .subscribe(translations => {
        this.taskLabel = translations['TaskLabel'];
        this.start_DateLabel = translations['StartDateLabel'];
        this.end_DateLabel = translations['EndDateLabel'];
      });
  }

  loadTasklist() {
    this.backendService.getAllTasklist().subscribe((data: any) => {
      this.allTaskList = data;
      this.screenLoader = false;
    });
  }

  onSelectManager(user: any) {
    this.selectedUser = user;
    this.taskmodel.users = user;
  }

  onSelectProject(project: any) {
    this.selectedProject = project;
    this.taskmodel.project = project;
  }

  onSelectParentTask(parentTask: any) {
    this.selectParentTask = parentTask;
    this.taskmodel.parent_Task = parentTask;
    this.taskmodel.parenTask = parentTask.parTask;
  }

  addtask(data) {
    if (this.isEdit) {
      this.UpdateTask();
      this.screenLoader = false;
      this.isEdit = true;
    } else {
      this.modelstatus = this.taskmodel.isparent;
      this.backendService.addtaskService(this.taskmodel)
        .subscribe((data: {}) => {
          this.router.navigate(['/addtask']);
          this.submitted = true;
          this.errormessage = "";
          this.modalHeading = this.modelstatus ? 'Parent Task Status' : 'Task Status';
          this.modalBody = this.modelstatus ? 'Parent Task Added Successfully' : 'Task Added Successfully';
          document.getElementById("submitModalOpener").click();
          this.taskmodel = {
            task: '',
            isparent: false,
            start_Date: '',
            end_Date: '',
            priority: '0',
            endTask: false,
            parenTask: '',
            parent_Task: ParentTask,
            project: Project,
            users: User
          }
          this.backendService.getAllParentTasklist().subscribe((data: any) => {
            this.allParentTaskList = data;
            this.screenLoader = false;
          });
          this.loadTasklist();
          this.taskmodel.start_Date = this.formatter.format(this.fromDate);
          this.taskmodel.end_Date = this.formatter.format(this.toDate);
        },
          error => {
            this.errormessage = error;
            if (error = "409") {
              this.modalHeading = 'Failed To Add Project';
              this.modalBody = 'Incorrect Task Details';
              document.getElementById("submitModalOpener").click();
            }
            this.submitted = false;
          })
    }
    this.taskmodel = {
      task: '',
      isparent: false,
      start_Date: '',
      end_Date: '',
      priority: '0',
      endTask: false,
      parenTask: '',
      parent_Task: ParentTask,
      project: Project,
      users: User
    }
  }
  resetButton() {
    this.submitted = false;
    this.taskmodel = {
      task: '',
      isparent: false,
      start_Date: '',
      end_Date: '',
      priority: '0',
      endTask: false,
      parenTask: '',
      parent_Task: ParentTask,
      project: Project,
      users: User
    }
    this.taskmodel.start_Date = this.formatter.format(this.fromDate);
    this.taskmodel.end_Date = this.formatter.format(this.toDate);
  }

  UpdateTask() {
    this.backendService.edittask(this.id, this.taskmodel)
      .subscribe((data: {}) => {
        this.submitted = true;
        this.errormessage = "";
        this.modalHeading = 'Task Status';
        this.modalBody = 'Updated Task Details Successfully';
        document.getElementById("submitModalOpener").click();
        this.loadTasklist();
        this.backendService.getAllParentTasklist().subscribe((data: any) => {
          this.allParentTaskList = data;
          this.screenLoader = false;
        });
      },
        error => {
          this.errormessage = error;
          this.submitted = false;
          this.isEdit = true;
        })
  }

  CancelTaskScreen() {
    this.resetButton();
    this.isEdit = false;
  }

  error: any = { isError: false, errorMessage: '' };

  compareTwoDates() {
    if (new Date(this.taskmodel.start_Date) > new Date(this.taskmodel.end_Date)) {
      this.error = { isError: true, errorMessage: "Start Date can't be after End date" };
    } else {
      this.error = { isError: false, errorMessage: "" };
    }
  }

}
