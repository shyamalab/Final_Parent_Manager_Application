import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchByUser } from './../SearchByUser';


@NgModule({
  declarations: [
    SearchByUser

  ],
  exports: [
    SearchByUser
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
