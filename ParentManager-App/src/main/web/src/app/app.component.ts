import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Router, ActivatedRoute, Params} from '@angular/router';
declare var jQuery:any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Parent Manager';

  onActivate(e:any, outlet:any){
    window.scrollTo(0,0);
  }

  constructor(
    public translate: TranslateService, public router: Router, 
    private activatedRoute: ActivatedRoute) {
    translate.addLangs(['English', 'French']);
    if (localStorage.getItem('locale')) {
      const browserLang = localStorage.getItem('locale');
      translate.use(browserLang.match(/English|French/) ? browserLang : 'English');
    } else {
      localStorage.setItem('locale', 'English');
      translate.setDefaultLang('English');
    }
  }
  changeLang(language: string) {
    localStorage.setItem('locale', language);
    this.translate.use(language);
  }

  addUser(){
    jQuery(".navig").removeClass('active-nav');
    jQuery("#add-user").addClass('active-nav');
    this.router.navigate(['/adduser']);
  }

  addProject(){
    jQuery(".navig").removeClass('active-nav');
    jQuery("#add-project").addClass('active-nav');
    this.router.navigate(['/addproject']);
  }

  addTask(){
    jQuery(".navig").removeClass('active-nav');
    jQuery("#add-task").addClass('active-nav');
    this.router.navigate(['/addtask']);
  }

  viewTask(){
    jQuery(".navig").removeClass('active-nav');
    jQuery("#view-task").addClass('active-nav');
    this.router.navigate(['/viewtask']);
  }
}