import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddOfferModalComponent } from './add-offer-modal.component';

describe('AddOfferModalComponent', () => {
  let component: AddOfferModalComponent;
  let fixture: ComponentFixture<AddOfferModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddOfferModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddOfferModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
