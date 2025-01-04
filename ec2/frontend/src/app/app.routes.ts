import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { MainScreenComponent } from './components/main-screen/main-screen.component';
import { OfferDetailsComponent } from './components/offer-details/offer-details.component';
import { RegisterComponent } from './components/register/register.component';
import {UserListComponent} from "./components/user-list/user-list.component";

export const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'main', component: MainScreenComponent },
    { path: 'offer-details/:id', component: OfferDetailsComponent },
    { path: 'users', component: UserListComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
