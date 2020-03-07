import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientModule, HttpClient } from '@angular/common/http';
 import { TaskuserModule } from './taskuser/taskuser.module';
 import { TaskprojectModule } from './taskproject/taskproject.module';
 import { AddtaskModule } from './addtask/addtask.module';
 import { ViewtaskModule } from './viewtask/viewtask.module';
import { NgbModal, NgbModule, NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [
    AppComponent

  ],

  imports: [
    HttpClientModule,
    BrowserModule,
    NgbModule,
    NgbTypeaheadModule,
    AddtaskModule,
    TaskprojectModule,
     TaskuserModule,
     ViewtaskModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
