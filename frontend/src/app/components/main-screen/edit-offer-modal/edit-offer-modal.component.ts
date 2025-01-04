import { Component, Inject } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AppFile } from '../../../models/file';
import { Offer } from '../../../models/offer';
import { OffersService } from '../../../services/offersService';
import { EditOfferModule } from './edit-offer-modal.module';

@Component({
  selector: 'app-edit-offer-modal',
  templateUrl: './edit-offer-modal.component.html',
  styleUrls: ['./edit-offer-modal.component.scss']
})
export class EditOfferModalComponent {
  title: string;
  description: string;
  files: AppFile[] | null;
  tags: string[];
  noticeStatus: string;
  sellerNumber: string;
  tagCtrl: FormControl = new FormControl([]);
  useExistingTags: boolean = false;
  ownTag: string;

  constructor(public dialogRef: MatDialogRef<EditOfferModalComponent>, @Inject(MAT_DIALOG_DATA) public data: { offer: Offer }, private offersService: OffersService,) {
    this.title = data.offer.title;
    this.description = data.offer.description;
    this.files = data.offer.files;
    this.tags = data.offer.tags;
    this.ownTag = data.offer.tags.join(", ");
    this.noticeStatus = data.offer.noticeStatus;
    this.sellerNumber = data.offer.sellerNumber;
    this.tagCtrl.setValue(this.tags);
  }

  ngOnInit(): void {
    this.offersService.getTags().subscribe(tags => {
      this.tags = tags;
    });
  }

  closeModal(): void {
    this.dialogRef.close();
  }

  editOffer(): void {
    let selectedTags = this.useExistingTags ? this.tagCtrl.value : this.ownTag.split(',').map(tag => tag.trim());
    this.offersService.editOffer(
      this.data.offer.id,
      this.data.offer.title,
      this.description,
      this.files,
      selectedTags,
      this.noticeStatus,
      this.sellerNumber,
    ).subscribe(
      () => {
        this.closeModal()
      },
      (error) => {
        console.error('Błąd podczas dodawania oferty:', error);
      }
    );
  }

}
