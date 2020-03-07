import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'projectSearchPipe', pure: false })
export class SearchByProject implements PipeTransform {

  transform(project: Array<any>, searchProject: any): any[] {

    if (project && project.length) {
      return project.filter(proj => {
        if (searchProject && proj.project.toLowerCase().indexOf(searchProject.toLowerCase()) === -1) {
          return false;
        }
        return true;
      })
    }
    else {
      return project;
    }
  }
}
