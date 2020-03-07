import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskprojectComponent } from './taskproject.component';

describe('TaskprojectComponent', () => {
  let component: TaskprojectComponent;
  let fixture: ComponentFixture<TaskprojectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TaskprojectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskprojectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
