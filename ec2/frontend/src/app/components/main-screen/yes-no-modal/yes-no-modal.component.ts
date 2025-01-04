import { Component, Inject, Input } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { OffersService } from '../../../services/offersService';

@Component({
  selector: 'app-yes-no-modal',
  standalone: true,
  imports: [],
  templateUrl: './yes-no-modal.component.html',
  styleUrl: './yes-no-modal.component.scss'
})
export class YesNoModalComponent {

  constructor(public dialogRef: MatDialogRef<YesNoModalComponent>, @Inject(MAT_DIALOG_DATA) public data: any, public offersService: OffersService) { }

  closeModal(): void {
    this.dialogRef.close();
  }

  deleteOffer(): void {
    this.offersService.deleteOffer(this.data.offerId).subscribe(
      () => {
        this.closeModal();
      },
      (error) => {
        console.error('Błąd podczas usuwania oferty:', error);
      }
    );
  }
}
