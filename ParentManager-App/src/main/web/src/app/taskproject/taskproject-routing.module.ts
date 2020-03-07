import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TaskprojectComponent } from './taskproject.component';

const routes: Routes = [{ path: '', component: TaskprojectComponent },
{ path: 'addproject', component: TaskprojectComponent },
{ path: 'editproject', component: TaskprojectComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TaskprojectRoutingModule { }
