import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddtaskComponent } from './addtask.component';

const routes: Routes = [{ path: '', component: AddtaskComponent },
{ path: 'addtask', component: AddtaskComponent },
{ path: 'edittask', component: AddtaskComponent },
{path: 'edittask/:id', component: AddtaskComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AddtaskRoutingModule { }
