import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditOfferModalComponent } from './edit-offer-modal.component';

describe('EditOfferModalComponent', () => {
  let component: EditOfferModalComponent;
  let fixture: ComponentFixture<EditOfferModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditOfferModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditOfferModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
