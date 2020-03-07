import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchByUser } from './../SearchByUser';
import { SearchByProject } from './../SearchByProject';
import { SearchByParent } from './../SearchByParent';
import { SearchTaskByProject } from './../SearchTaskByProject';

@NgModule({
  declarations: [
    SearchByUser,
    SearchByProject,
    SearchByParent,
    SearchTaskByProject

  ],
  exports: [
    SearchByUser,
    SearchByProject,
    SearchByParent,
    SearchTaskByProject
  ],
  imports: [
    CommonModule
  ]
})
export class SharepipemoduleModule { 
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharepipemoduleModule,
    };
  }
}
