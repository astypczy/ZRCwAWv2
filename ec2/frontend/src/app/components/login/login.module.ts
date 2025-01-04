import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login.component';
import { AppRoutingModule } from '../../app.routes';
import { AuthService } from '../../services/authService';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
    declarations: [
        LoginComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        AppRoutingModule,
        HttpClientModule
    ],
    providers: [AuthService],
    bootstrap: [LoginComponent]
})
export class LoginModule { }
