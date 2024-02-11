import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatedHomeComponent } from './updated-home.component';

describe('UpdatedHomeComponent', () => {
  let component: UpdatedHomeComponent;
  let fixture: ComponentFixture<UpdatedHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdatedHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdatedHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
