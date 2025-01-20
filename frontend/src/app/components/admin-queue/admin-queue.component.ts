import { Component, OnInit } from '@angular/core';
import { AdminQueueService } from "../../services/admin-queue.service";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-admin-queue',
  templateUrl: './admin-queue.component.html',
  standalone: true,
  styleUrls: ['./admin-queue.component.scss'],
  imports: [CommonModule]
})
export class AdminQueueComponent implements OnInit {
  messages: string[] = [];

  constructor(private adminQueueService: AdminQueueService) {}

  ngOnInit(): void {
    this.loadMessages();
  }

  loadMessages(): void {
    this.adminQueueService.getAdminMessages().subscribe({
      next: (data) => {
        this.messages = data;
      },
      error: (err) => {
        console.error('Error fetching messages:', err);
      }
    });
  }
}
