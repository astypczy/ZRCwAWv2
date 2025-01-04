export interface User {
    id?: number;
    firstName: string;
    surname: string;
    password?: string;
    email: string;
    isSeller: boolean;
    login: string;
    cognitoSub?: string;
}
