import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { YesNoModalComponent } from './yes-no-modal.component';

@NgModule({
    declarations: [
        YesNoModalComponent
    ],
    imports: [
        BrowserModule,
        FormsModule
    ],
    providers: [],
    bootstrap: [YesNoModalComponent]
})
export class YesNoModule { }
