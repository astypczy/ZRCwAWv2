import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminQueueComponent } from './admin-queue.component';

describe('AdminQueueComponent', () => {
  let component: AdminQueueComponent;
  let fixture: ComponentFixture<AdminQueueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminQueueComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminQueueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
