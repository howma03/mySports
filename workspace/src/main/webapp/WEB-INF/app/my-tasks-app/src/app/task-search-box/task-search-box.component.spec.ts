import {async, ComponentFixture, TestBed} from "@angular/core/testing";
import {TaskSearchBoxComponent} from "./task-search-box.component";

describe('TaskSearchBoxComponent', () => {
  let component: TaskSearchBoxComponent;
  let fixture: ComponentFixture<TaskSearchBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TaskSearchBoxComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TaskSearchBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
