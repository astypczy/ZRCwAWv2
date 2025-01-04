import { Component, OnInit,  } from '@angular/core';
import {User} from "../../models/user";
import {AuthService} from "../../services/authService";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent {
  users: User[] = [];
  constructor(private authService: AuthService) {
  }
  ngOnInit():void{
    this.loadUsers();
  }

  loadUsers() {
    this.authService.getAllUsers().subscribe({
      next: (users: User[]) => {
        this.users = users;
      },
      error: (error) => {
        console.error("Błąd podczas pobierania użytkowników", error);
      }
    });
  }
}
