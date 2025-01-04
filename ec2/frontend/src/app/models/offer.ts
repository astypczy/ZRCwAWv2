import { AppFile } from "./file";

export interface Offer {
    id: number;
    title: string;
    description: string;
    files: AppFile[] | null;
    tags: string[];
    noticeStatus: 'Draft' | 'Live' | 'Archived';
    createdBy: string;
    sellerNumber: string;
}
