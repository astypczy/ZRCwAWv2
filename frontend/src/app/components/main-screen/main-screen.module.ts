import { NgModule } from '@angular/core';
import { MainScreenComponent } from './main-screen.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@NgModule({
    declarations: [
        MainScreenComponent
    ],
    imports: [
        CommonModule,
        FormsModule
    ],
    bootstrap: [MainScreenComponent]
})
export class MainScreenModule { }
