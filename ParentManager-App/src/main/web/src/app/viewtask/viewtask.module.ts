import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharepipemoduleModule } from '../pipes/sharepipemodule/sharepipemodule.module';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { NgbModule, NgbTypeaheadModule} from '@ng-bootstrap/ng-bootstrap';
import { ViewtaskComponent } from './viewtask.component';
import { ViewtaskRoutingModule } from './viewtask-routing.module';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [ViewtaskComponent],
  imports: [
    CommonModule,
    SharepipemoduleModule.forRoot(),
    ViewtaskRoutingModule, NgbModule, NgbTypeaheadModule,
    FormsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  exports: [ViewtaskComponent, FormsModule]
})
export class ViewtaskModule { }
