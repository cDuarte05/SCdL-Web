import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultUpload } from './default-upload';

describe('DefaultUpload', () => {
  let component: DefaultUpload;
  let fixture: ComponentFixture<DefaultUpload>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DefaultUpload]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DefaultUpload);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
