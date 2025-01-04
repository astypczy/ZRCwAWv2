import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Offer } from '../models/offer';
import { Observable } from 'rxjs';
import { AppFile } from '../models/file';
import { AuthService } from './authService';

const offerApiPrefix = 'http://107.21.128.230:8080/api/notices';

@Injectable({
    providedIn: 'root'
})
export class OffersService {

    constructor(private readonly http: HttpClient, private authService: AuthService) { }

    private getHeaders(): HttpHeaders {
        return this.authService.getAuthHeaders();
    }

    addOffer(
        title: string,
        description: string,
        tags: string[],
        noticeStatus: string,
        sellerNumber: string,
    ): Observable<Offer> {
        return this.http.post<Offer>(`${offerApiPrefix}`, { title, description, tags, noticeStatus, sellerNumber }, { headers: this.getHeaders() });
    }

    addFilesToNotice(noticeId: number, files: AppFile[]): Observable<any> {
        const filePayload = files.map(file => ({
            fileName: file.fileName,
            fileType: file.fileType,
            fileContent: file.fileContent,
        }));
        return this.http.post(`${offerApiPrefix}/${noticeId}/files`, filePayload, { headers: this.getHeaders() });
    }

    getAllFilesForNotice(noticeId: number): Observable<AppFile[]> {
        return this.http.get<AppFile[]>(`${offerApiPrefix}/${noticeId}/files`, { headers: this.getHeaders() });
    }

    getAllOffers(): Observable<Offer[]> {
        return this.http.get<Offer[]>(`${offerApiPrefix}`, { headers: this.getHeaders() });
    }

    getOfferById(id: number): Observable<Offer> {
        return this.http.get<Offer>(`${offerApiPrefix}/${id}`, { headers: this.getHeaders() });
    }

    deleteOffer(id: number): Observable<any> {
        return this.http.delete(`${offerApiPrefix}/${id}`, { headers: this.getHeaders(), responseType: 'text' });
    }

    editOffer(
        id: number,
        title: string,
        description: string,
        files: AppFile[] | null,
        tags: string[],
        noticeStatus: string,
        sellerNumber: string,
    ): Observable<any> {
        return this.http.put(`${offerApiPrefix}/${id}`, { title, description, files, tags, noticeStatus, sellerNumber }, { headers: this.getHeaders(), responseType: 'text' });
    }

    getFilteredOffers(criteria: any): Observable<Offer[]> {
        return this.http.post<Offer[]>(`${offerApiPrefix}/filter`, criteria, { headers: this.getHeaders() });
    }

    getTags(): Observable<string[]> {
        return this.http.get<string[]>(`${offerApiPrefix}/tags`, { headers: this.getHeaders() });
    }
}
