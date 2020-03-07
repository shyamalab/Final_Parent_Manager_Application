import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TaskuserComponent } from './taskuser.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      { path: '', component: TaskuserComponent },
      { path: 'adduser', component: TaskuserComponent },
      { path: 'edituser', component: TaskuserComponent }
    ])
  ],
  exports: [RouterModule]
})
export class TaskuserRoutingModule { }
