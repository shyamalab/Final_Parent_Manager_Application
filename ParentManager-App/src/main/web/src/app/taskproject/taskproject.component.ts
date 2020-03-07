import { Component, OnInit, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-taskproject',
  templateUrl: './taskproject.component.html',
  styleUrls: ['./taskproject.component.css']
})
export class TaskprojectComponent implements OnInit {

  constructor(private translate: TranslateService) {
  }

  ngOnInit() {
    
  }

}
