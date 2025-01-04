import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Offer } from '../../models/offer';
import { OffersService } from '../../services/offersService';
import { AddOfferModalComponent } from './add-offer-modal/add-offer-modal.component';
import { EditOfferModalComponent } from './edit-offer-modal/edit-offer-modal.component';
import { MainScreenModule } from './main-screen.module';
import { YesNoModalComponent } from './yes-no-modal/yes-no-modal.component';

import { AuthService } from '../../services/authService';
import { ToastrService } from 'ngx-toastr';
import { User } from '../../models/user';

@Component({
  selector: 'app-main-screen',
  templateUrl: './main-screen.component.html',
  styleUrls: ['./main-screen.component.scss']
})
export class MainScreenComponent implements OnInit {
  offers: Offer[] = [];
  currentUser: User | null = null;
  filterTitle: string = '';
  filterTags: string = '';
  filterDescription: string = '';
  constructor(private router: Router, public dialog: MatDialog, private offersService: OffersService,
    private authService: AuthService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.loadOffers();
    this.loadCurrentUser();
  }

  loadOffers(): void {
    this.offersService.getAllOffers().subscribe(
      (data: Offer[]) => {
        this.offers = data;
      },
      (error) => {
        console.error('Błąd podczas pobierania ofert:', error);
      }
    );
  }

  loadCurrentUser(): void {
    this.authService.getCurrentUser().subscribe(
      (user: User) => {
        this.currentUser = user;
      },
      (error) => {
        console.error('Błąd podczas pobierania informacji o użytkowniku:', error);
      }
    );
  }

  logout() {
    this.authService.logout().subscribe(() => {
      this.router.navigate(['']);
    });
  }

  openModal(): void {
    const dialogRef = this.dialog.open(AddOfferModalComponent, {
      height: "80%", width: "50%"
    });
    dialogRef.afterClosed().subscribe(() => {
      this.loadOffers();
    });
  }

  openDeleteModal(offerId: number): void {
    const dialogRef = this.dialog.open(YesNoModalComponent, {
      height: "30%", width: "50%",
      data: { offerId: offerId }
    });
    dialogRef.afterClosed().subscribe(() => {
      this.loadOffers();
    });
  }

  openEditModal(offerId: number): void {
    const selectedOffer = this.offers.find(offer => offer.id === offerId);
    const dialogRef = this.dialog.open(EditOfferModalComponent, {
      height: "80%", width: "50%",
      data: { offer: selectedOffer }
    });
    dialogRef.afterClosed().subscribe(() => {
      this.loadOffers();
    });
  }


  openOfferDetails(offerId: number): void {
    this.router.navigate(['/offer-details', offerId]);
  }

  applyFilters(): void {
    const criteria = [];

    if (this.filterTitle) {
      criteria.push({ searchKey: 'TITLE', searchValue: this.filterTitle, operator: 'EQUALS' });
    }
    if (this.filterTags) {
      criteria.push({ searchKey: 'TAGS', searchValue: this.filterTags, operator: 'CONTAINS' });
    }
    if (this.filterDescription) {
      criteria.push({ searchKey: 'DESCRIPTION', searchValue: this.filterDescription, operator: 'LIKE' });
    }

    this.offersService.getFilteredOffers({ searchCriteria: criteria }).subscribe(
      (data: Offer[]) => {
        this.offers = data;
      },
      (error: any) => {
        console.error('Błąd podczas filtrowania ofert:', error);
      }
    );
  }

  clearFilters(): void {
    this.filterTitle = '';
    this.filterTags = '';
    this.filterDescription = '';
    this.loadOffers();
  }

  getNoticeStatusText(status: string): string {
    switch (status) {
      case 'Draft':
        return 'Wersja robocza';
      case 'Live':
        return 'Opublikowane';
      case 'Archived':
        return 'Archiwum';
      default:
        return status;
    }
  }

  showUsers() {
    this.router.navigate(["/users"]);
  }
}
