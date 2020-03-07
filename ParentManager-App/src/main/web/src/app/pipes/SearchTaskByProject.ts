import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'projectSearchTaskPipe', pure: false })
export class SearchTaskByProject implements PipeTransform {

  transform(task: Array<any>, searchProject1: any): any[] {

    if (task && task.length) {
      return task.filter(projtask => {
        if (searchProject1 && projtask.project.project.toLowerCase().indexOf(searchProject1.toLowerCase()) === -1) {
          return false;
        }
        return true;
      })
    }
    else {
      return task;
    }
  }
}

