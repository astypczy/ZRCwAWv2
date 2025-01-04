import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, catchError, first, from, map, Observable, of, switchMap, throwError, tap } from 'rxjs';
import { User } from '../models/user';
import { signUp, signIn, signOut, getCurrentUser, fetchAuthSession, fetchUserAttributes } from '@aws-amplify/auth';


const authApiPrefix = 'http://107.21.128.230:8080/api/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {}


  login(email: string, password: string): Observable<any> {
    return from(signIn({ username: email, password })).pipe(
      switchMap((signInResult) => {
        console.log('SignIn result:', signInResult); // Logowanie wyniku z signIn()

        return from(fetchAuthSession()).pipe(
          switchMap((session) => {
            console.log('Fetched session:', session); // Logowanie pełnej sesji

            if (session && session.tokens?.accessToken && session.tokens?.idToken) {
              // Zapisz tokeny do localStorage
              localStorage.setItem('accessToken', session.tokens.accessToken.toString());
              localStorage.setItem('idToken', session.tokens.idToken.toString());
              console.log('Access token:', session.tokens.accessToken.toString());
              console.log('ID token:', session.tokens.idToken.toString());

              // Pobierz atrybuty użytkownika
              return from(fetchUserAttributes()).pipe(
                switchMap((attributes) => {
                  // Synchronizuj z backendem
                  return this.syncUserWithBackend(attributes).pipe(
                    catchError((backendError) => {
                      console.error('Error syncing with backend:', backendError);
                      return of(null); // Obsługa błędu synchronizacji
                    })
                  );
                }),
                catchError((attributesError) => {
                  console.error('Error fetching user attributes:', attributesError);
                  return of(null); // Obsługa błędu pobierania atrybutów
                })
              );
            } else {
              console.error('No tokens found in session');
              return of(null); // Obsługa przypadku braku tokenów
            }
          }),
          catchError((sessionError) => {
            console.error('Error fetching auth session:', sessionError);
            return of(null); // Obsługa błędu sesji
          })
        );
      }),
      catchError((signInError) => {
        console.error('Error signing in:', signInError);
        return of(null); // Obsługa błędu logowania
      })
    );
  }

  private syncUserWithBackend(cognitoAttributes: Record<string, any>): Observable<Partial<User>> {
    console.log('Syncing user with backend, Cognito attributes:', cognitoAttributes);
    const user: Partial<User> = {
      email: cognitoAttributes['email'],
      firstName: cognitoAttributes['given_name'],
      surname: cognitoAttributes['family_name'],
      isSeller: cognitoAttributes['custom:isSeller'] === 'true',
      login: cognitoAttributes['email'],
      cognitoSub: cognitoAttributes['sub']
    };

    console.log('Prepared user object for backend:', user);

    return this.http.post<User>(`${authApiPrefix}/sync-user`, user)
      .pipe(
        tap(response => console.log('Backend sync response:', response)),
        catchError(error => {
          console.error('Backend sync error:', error);
          console.error('Error details:', {
            status: error.status,
            statusText: error.statusText,
            message: error.message,
            error: error.error
          });
          return throwError(() => error);
        })
      );
  }

  logout(): Observable<void> {
    return from(signOut()).pipe(
      map(() => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('idToken');
      }),
      catchError((error) => {
        console.error('Error signing out:', error);
        return throwError(() => error);
      })
    );
  }

  async isAuthenticated(): Promise<boolean> {
    try {
      const user = await getCurrentUser();
      return !!user;
    } catch {
      return false;
    }
  }

  getAuthHeaders(): HttpHeaders {
    const idToken = this.getIdToken();  // Use ID token instead of access token
    return new HttpHeaders({
      'Authorization': `Bearer ${idToken}`,
      'Content-Type': 'application/json'
    });
  }

  getAccessToken(): string | null {
    if (typeof window !== 'undefined' && window.localStorage) {
      return localStorage.getItem('accessToken');
    }
    return null;
  }

  getIdToken(): string | null {
    if (typeof window !== 'undefined' && window.localStorage) {
      return localStorage.getItem('idToken');
    }
    return null;
  }

  register(firstname: string, surname: string, login: string, password: string, isSeller: boolean): Observable<any> {
    console.log('Starting registration process for:', login);
    return from(signUp({
      username: login,
      password,
      options: {
        userAttributes: {
          email: login,
          'given_name': firstname,
          'family_name': surname,
          'custom:isSeller': isSeller.toString()
        }
      }
    })).pipe(
      tap(result => console.log('Cognito registration result:', result)),
      switchMap((cognitoResult) => {
        console.log('Logging in after registration');
        return this.login(login, password);
      }),
      switchMap(() => {
        console.log('Syncing with backend after login');
        const user: Partial<User> = {
          email: login,
          firstName: firstname,
          surname: surname,
          isSeller: isSeller,
          login: login
        };
        return this.http.post<User>(`${authApiPrefix}/sync-user`, user);
      }),
      catchError((error) => {
        console.error('Registration error:', error);
        console.error('Full error object:', JSON.stringify(error, null, 2));
        return throwError(() => error);
      })
    );
  }

  getCurrentUser(): Observable<User> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.getIdToken()}`, // Cognito token
      'Content-Type': 'application/json'
    })

    console.log('Token being set:', this.getIdToken());
    return this.http.get<User>(`${authApiPrefix}/current-user`, {
      headers: headers
      //withCredentials: true
    }).pipe(
      catchError((error: any) => {
        console.error("Error getting current user:", error);
        return throwError(() => error);
      })
    );
    }

  // return this.http.get<User>(`${authApiPrefix}/current-user`, { headers: this.getAuthHeaders() });
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${authApiPrefix}/users`, { headers: this.getAuthHeaders() });
  }

}
