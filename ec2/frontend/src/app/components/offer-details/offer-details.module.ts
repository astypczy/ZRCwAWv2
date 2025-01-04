import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { OfferDetailsComponent } from './offer-details.component';

@NgModule({
  declarations: [
    OfferDetailsComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  providers: [DatePipe]
})
export class OfferDetailsModule { }
