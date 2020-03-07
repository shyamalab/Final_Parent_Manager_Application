import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'parentSearchPipe', pure: false })
export class SearchByParent implements PipeTransform {

  transform(parentTask: Array<any>, searchParent: any): any[] {

    if (parentTask && parentTask.length) {
      return parentTask.filter(parent => {
        if (searchParent && parent.parTask.toLowerCase().indexOf(searchParent.toLowerCase()) === -1) {
          return false;
        }
        return true;
      })
    }
    else {
      return parentTask;
    }
  }
}
