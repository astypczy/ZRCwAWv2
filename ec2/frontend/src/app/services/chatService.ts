import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './authService';
import { Message } from '../models/message';

@Injectable({
    providedIn: 'root'
})
export class ChatService {
    private apiUrl = 'http://107.21.128.230:8080/api/chat';

    constructor(private http: HttpClient, private authService: AuthService) { }

    private getHeaders(): HttpHeaders {
        return this.authService.getAuthHeaders();
    }

    getMessages(offerId: number, userId: string): Observable<Message[]> {
        return this.http.get<Message[]>(`${this.apiUrl}/messages/${offerId}?userId=${userId}`, { headers: this.getHeaders() });
    }

    sendMessage(offerId: number, message: string, recipientId: string, senderId: string, senderName: string, timestamp: number): Observable<void> {
        return this.http.post<void>(`${this.apiUrl}/messages/${offerId}`, { message, recipientId, senderId, senderName, timestamp }, { headers: this.getHeaders() });
    }

    getUserMessages(userId: string): Observable<Message[]> {
        return this.http.get<Message[]>(`${this.apiUrl}/user-messages/${userId}`, { headers: this.getHeaders() });
    }
}
