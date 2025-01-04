import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OffersService } from '../../services/offersService';
import { Offer } from '../../models/offer';
import { ChatService } from '../../services/chatService';
import { Message } from '../../models/message';
import { AuthService } from '../../services/authService';
import { User } from '../../models/user';
import { OfferDetailsModule } from './offer-details.module';
import { AppFile } from '../../models/file';

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.scss']
})
export class OfferDetailsComponent implements OnInit {
  offer: Offer | null = null;
  files: AppFile[] = [];
  messages: Message[] = [];
  isChatOpen: boolean = false;
  newMessage: string = '';
  currentUser: User | null = null;
  replyMessage: { [key: string]: string } = {};
  replyMode: { [key: string]: boolean } = {};

  constructor(
    private route: ActivatedRoute,
    private offersService: OffersService,
    private chatService: ChatService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      const offerId = id ? +id : null;
      if (offerId !== null && !isNaN(offerId)) {
        this.loadOfferDetails(offerId);
        this.loadFiles(offerId);
        this.loadCurrentUser(offerId);
      } else {
        console.error('Invalid offer ID');
        this.goBack();
      }
    });
  }

  loadOfferDetails(offerId: number): void {
    this.offersService.getOfferById(offerId).subscribe(
      (offer: Offer) => {
        this.offer = offer;
      },
      (error) => {
        console.error('Błąd podczas pobierania szczegółów oferty:', error);
      }
    );
  }

  loadFiles(offerId: number): void {
    this.offersService.getAllFilesForNotice(offerId).subscribe(
      (files: AppFile[]) => {
        this.files = files;
      },
      (error) => {
        console.error('Błąd podczas pobierania plików:', error);
      }
    );
  }

  loadMessages(offerId: number): void {
    const userId = this.currentUser ? this.currentUser.login : '';
    this.chatService.getMessages(offerId, userId).subscribe(
      (messages: Message[]) => {
        this.messages = messages;
      },
      (error) => {
        console.error('Błąd podczas pobierania wiadomości:', error);
      }
    );
  }

  loadCurrentUser(offerId: number): void {
    this.authService.getCurrentUser().subscribe(
      (user: User) => {
        this.currentUser = user;
        this.loadMessages(offerId);
      },
      (error) => {
        console.error('Błąd podczas pobierania aktualnego użytkownika:', error);
      }
    );
  }

  toggleChat(): void {
    this.isChatOpen = !this.isChatOpen;
  }

  sendMessage(): void {
    if (this.newMessage.trim()) {
      let recipientId = '';
      if (this.currentUser!.email === this.offer!.createdBy) {
        recipientId = this.messages.length > 0 ? this.messages[0].senderId : this.offer!.createdBy;
      } else {
        recipientId = this.offer!.createdBy;
      }
      const senderId = this.currentUser!.login;
      const senderName = this.currentUser!.firstName + ' ' + this.currentUser!.surname;
      const timestamp = Date.now();
      this.chatService.sendMessage(this.offer!.id, this.newMessage, recipientId, senderId, senderName, timestamp).subscribe(
        () => {
          const newMessage: Message = {
            message: this.newMessage,
            senderId: senderId,
            recipientId: recipientId,
            timestamp: timestamp,
            senderName: senderName
          };
          this.messages.push(newMessage);
          this.newMessage = '';
        },
        (error: any) => {
          console.error('Błąd podczas wysyłania wiadomości:', error);
        }
      );
    }
  }

  replyToMessage(message: Message): void {
    const recipientId = message.senderId;
    const senderId = this.currentUser!.login;
    const senderName = this.currentUser!.firstName + ' ' + this.currentUser!.surname;
    const reply = this.replyMessage[message.timestamp.toString()];

    if (reply && reply.trim()) {
      const timestamp = Date.now();
      this.chatService.sendMessage(this.offer!.id, reply, recipientId, senderId, senderName, timestamp).subscribe(
        () => {
          const newMessage: Message = {
            message: reply,
            senderId: senderId,
            recipientId: recipientId,
            timestamp: timestamp,
            senderName: senderName
          };
          this.messages.push(newMessage);
          this.replyMessage[message.timestamp.toString()] = '';
        },
        (error: any) => {
          console.error('Błąd podczas wysyłania odpowiedzi:', error);
        }
      );
    }
  }

  goBack(): void {
    this.router.navigate(['main']);
  }
}
