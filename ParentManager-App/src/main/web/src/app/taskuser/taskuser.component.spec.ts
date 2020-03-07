import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskuserComponent } from './taskuser.component';

describe('TaskuserComponent', () => {
  let component: TaskuserComponent;
  let fixture: ComponentFixture<TaskuserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TaskuserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskuserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
