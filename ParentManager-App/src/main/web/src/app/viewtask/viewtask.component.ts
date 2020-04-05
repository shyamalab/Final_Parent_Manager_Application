import { Component, OnInit} from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { BackendService } from "./../backend.service";
import { Router } from '@angular/router';
import { Project } from './../Project';
import * as _ from 'underscore';

@Component({
  selector: 'app-viewtask',
  templateUrl: './viewtask.component.html',
  styleUrls: ['./viewtask.component.css']
})
export class ViewtaskComponent implements OnInit {

  searchProject1: String;
  allTaskList: any = [];
  allProjectList: any = [];
  screenLoader: boolean = false;
  selectedProject: Project;
  Task: any;

  submitted: boolean = false;
  errormessage: string;
  modalHeading: string;
  modalBody: string;

  constructor(private backendService: BackendService,
    public router: Router,
    private translate: TranslateService) {
    backendService.getAllTasklist().subscribe((data: any) => {
      this.allTaskList = data;
      this.screenLoader = false;
    });
    backendService.getAllProjectlist().subscribe((data: any) => {
      this.allProjectList = data;
      this.screenLoader = false;
    });
  }


  ngOnInit() {
  }

  onSelectProject(project: any) {
    this.selectedProject = project;
    this.searchProject1 = project.project;
  }

  loadTasklist() {
    return this.backendService.getAllTasklist().subscribe((data: any) => {
      this.allTaskList = data;
      this.screenLoader = true;
    });
  }

  suspendTask(task: any) {
    this.screenLoader = true;
    task.endTask = true;
    this.Task = task;
    this.backendService.edittask(this.Task.taskID, this.Task)
      .subscribe((data: {}) => {
        this.router.navigate(['/viewtask']);
        this.submitted = true;
        this.errormessage = "";
        this.modalHeading = 'Task Status';
        this.modalBody = 'Ended Task Successfully';
        document.getElementById("submitModalOpener1").click();
        this.loadTasklist();
      },
        error => {
          this.errormessage = error;
          this.submitted = false;
        })
  }

  onClickSortTask(event) {
    var target = event.target || event.srcElement || event.currentTarget;
    var idAttr = target.attributes.id;
    var value = idAttr.nodeValue;
    this.allTaskList = _.sortBy(this.allTaskList, value.substring(0, value.length - 1));
  }

  onClickSortTaskPriority() {
   this.allTaskList = this.allTaskList.sort(function(a, b) { 
     return a.priority- b.priority;
     })
  }
}
