import {async, ComponentFixture, TestBed} from "@angular/core/testing";
import {ColorPreviewerComponent} from "./color-previewer.component";

describe('ColorPreviewerComponent', () => {
  let component: ColorPreviewerComponent;
  let fixture: ComponentFixture<ColorPreviewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ColorPreviewerComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ColorPreviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
