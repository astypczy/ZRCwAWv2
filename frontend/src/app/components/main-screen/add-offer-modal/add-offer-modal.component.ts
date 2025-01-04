import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { AppFile } from '../../../models/file';
import { OffersService } from '../../../services/offersService';
import { AddOfferModule } from './add-offer-modal.module';

@Component({
  selector: 'app-add-offer-modal',
  templateUrl: './add-offer-modal.component.html',
  styleUrls: ['./add-offer-modal.component.scss']
})
export class AddOfferModalComponent {
  title: string;
  description: string;
  files: AppFile[] | null;
  tags: string[] = [];
  noticeStatus: string;
  sellerNumber: string;
  tagCtrl = new FormControl([]);
  useExistingTags: boolean = false;
  newTag: string = '';

  constructor(public dialogRef: MatDialogRef<AddOfferModalComponent>, private offersService: OffersService,) {
    this.title = '';
    this.description = '';
    this.files = null;
    this.noticeStatus = '';
    this.sellerNumber = '';
  }

  ngOnInit(): void {
    this.offersService.getTags().subscribe(tags => {
      this.tags = tags;
    });
  }


  closeModal(): void {
    this.dialogRef.close();
  }

  onFileSelected(event: any): void {
    const selectedFiles: FileList = event.target.files;
    this.files = [];
    for (let i = 0; i < selectedFiles.length; i++) {
      const file = selectedFiles.item(i);
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const fileContent = e.target.result.split(',')[1];
        this.files!.push({
          fileName: file!.name,
          fileType: file!.type,
          fileContent: fileContent,
        });
      };
      reader.readAsDataURL(file!);
    }
  }

  addOffer(): void {
    let selectedTags = this.useExistingTags ? this.tagCtrl.value : this.newTag.split(',').map(tag => tag.trim());
    this.offersService.addOffer(
      this.title,
      this.description,
      selectedTags!,
      this.noticeStatus,
      this.sellerNumber,).subscribe(
        (createdNotice) => {
          if (this.files && this.files.length > 0) {
            this.files = this.files.map(file => ({ ...file, noticeId: createdNotice.id }));
            this.offersService.addFilesToNotice(createdNotice.id, this.files!).subscribe(
              () => {
                this.closeModal();
              },
              (error) => {
                console.error('Error uploading files:', error);
              }
            );
          } else {
            this.closeModal();
          }
        },
        (error) => {
          console.error('Error creating notice:', error);
        }
      );
  }

}
