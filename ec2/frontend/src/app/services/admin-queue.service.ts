import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminQueueService {
  private apiUrl = 'http://107.21.128.230:8080/api/admin/messages'; // URL do endpointa backendowego

  constructor(private http: HttpClient) {}

  // Pobierz wiadomości z admin-queue
  getAdminMessages(): Observable<string[]> {
    return this.http.get<string[]>(this.apiUrl);
  }
}
