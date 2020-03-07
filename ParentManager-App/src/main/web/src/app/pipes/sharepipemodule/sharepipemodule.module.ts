import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchByUser } from './../SearchByUser';
import { SearchByProject } from './../SearchByProject';

@NgModule({
  declarations: [
    SearchByUser,
    SearchByProject

  ],
  exports: [
    SearchByUser,
    SearchByProject
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
