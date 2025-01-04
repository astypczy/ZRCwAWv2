import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RegisterComponent } from './register.component';
import { AppRoutingModule } from '../../app.routes';
import { AuthService } from '../../services/authService';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
    declarations: [
        RegisterComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        AppRoutingModule,
        HttpClientModule
    ],
    providers: [AuthService],
    bootstrap: [RegisterComponent]
})
export class RegisterModule { }
