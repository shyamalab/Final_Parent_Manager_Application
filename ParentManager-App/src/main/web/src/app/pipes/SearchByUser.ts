import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'userSearchPipe', pure: false })
export class SearchByUser implements PipeTransform {

  transform(users: Array<any>, searchUser: any): any[] {

    if (users && users.length) {

      return users.filter(user => {
        if (searchUser && user.firstName.toLowerCase().indexOf(searchUser.toLowerCase()) === -1) {
          if (searchUser && user.lastName.toLowerCase().indexOf(searchUser.toLowerCase()) === -1) {
            return false;
          }
        }
        return true;
      })
    }
    else {
      return users;
    }
  }
}
