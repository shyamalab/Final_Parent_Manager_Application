import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgbModal, NgbModule, NgbTypeaheadModule, NgbDatepicker} from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { SharepipemoduleModule } from '../pipes/sharepipemodule/sharepipemodule.module';
import { TaskprojectRoutingModule } from './taskproject-routing.module';
import { TaskprojectComponent } from './taskproject.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  imports: [TaskprojectRoutingModule, FormsModule, CommonModule, NgbModule, NgbTypeaheadModule,
    SharepipemoduleModule.forRoot(),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })],
  declarations: [TaskprojectComponent],
  exports: [TaskprojectComponent, FormsModule],
  providers: []
})
export class TaskprojectModule { }
