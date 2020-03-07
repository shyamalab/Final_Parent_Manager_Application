import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharepipemoduleModule } from '../pipes/sharepipemodule/sharepipemodule.module';
import { AddtaskRoutingModule } from './addtask-routing.module';
import { AddtaskComponent } from './addtask.component';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { NgbModal, NgbModule, NgbTypeaheadModule, NgbDatepicker} from '@ng-bootstrap/ng-bootstrap';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [AddtaskComponent],
  imports: [
    CommonModule,
    SharepipemoduleModule.forRoot(),
    AddtaskRoutingModule, NgbModule, NgbTypeaheadModule,
    FormsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  exports: [AddtaskComponent, FormsModule]
})
export class AddtaskModule { }
