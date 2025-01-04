import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/authService';
import { LoginModule } from './login.module';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-root',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  email: string;
  password: string;
  showPassword: boolean = false;

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) {
    this.email = '';
    this.password = '';
  }

  login() {
    this.authService.login(this.email, this.password).subscribe({
      next: (response: any) => {
        const accessToken = response.accessToken;
        localStorage.setItem('accessToken', accessToken);
        this.router.navigate(['/main']);
      },
      error: (error: any) => {
        console.error('Błąd logowania:', error);
        if (error.status === 403) {
          this.showError('Błędny login lub hasło.', 'Błąd logowania');
        }
        else if(error.status === 400)        {
          this.showError('Brak tokenu, Bad request', 'Błąd logowania');
        }
        else {
          this.showError('Spróbuj ponownie później.', 'Błąd logowania')
        }
      },
      complete: () => {
        this.showSuccess('Zalogowano pomyślnie', 'Sukces');
      }
    });
  }
  showError(message: string, title: string) {
    this.toastr.error(message, title);
  }
  showSuccess(message: string, title: string) {
    this.toastr.success(message, title);
  }

}
