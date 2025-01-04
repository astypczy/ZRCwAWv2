import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/authService';
import { RegisterModule } from './register.module';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-root',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  firstName: string;
  surname: string;
  email: string;
  password: string;
  confirmPassword: string;
  isSeller: boolean;
  registerErrorPassword: string;
  registerErrorConfirmedPassword: string;
  showPassword: boolean = false;
  showPasswordConfirmation: boolean = false;
  submitted: boolean = false;

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) {
    this.firstName = '';
    this.surname = '';
    this.email = '';
    this.password = '';
    this.confirmPassword = '';
    this.registerErrorPassword = '';
    this.registerErrorConfirmedPassword = '';
    this.isSeller = false;
  }

  register() {
    this.authService.register(this.firstName, this.surname, this.email, this.password, this.isSeller).subscribe({
      next: () => {
        this.toastr.success('Rejestrcja zakończona sukcesem', 'Sukces');
        this.router.navigate(['/']);
      },
      error: (error) => {
      console.error('Bład rejestracji:', error);
      this.toastr.error('Bład rejestracji', 'Spróbuj ponownie');
      }
    });
  }



  goToLoginScreen() {
    this.router.navigate(['/']);
  }





  }



  // showError(message: string, title: string) {
  //   this.toastr.error(message, title);
  // }
  // showSuccess(message: string, title: string) {
  //   this.toastr.success(message, title);
  // }

  // isPasswordValid(): boolean {
  //   const passwordRegex = /^(?=.*[a-ząęćłńóśźż])(?=.*[A-ZĄĘĆŁŃÓŚŹŻ])(?=.*[!@#$%^&*])[a-zA-ZąęćłńóśźżĄĘĆŁŃÓŚŹŻ\d!@#$%^&*]{8,}$/;
  //   return passwordRegex.test(this.password);
  // }
  // arePasswordsMatching(): boolean {
  //   return this.password === this.confirmPassword;
//   }
// }
