import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrdersComponent } from './Components/orders/orders.component';
import { RegistrationComponent } from './Components/registration/registration.component';
import { UpdatedHomeComponent } from './Components/updated-home/updated-home.component';
import { ProfileComponent } from './Components/profile/profile.component';
import { UsersComponent } from './Components/users/users.component';
import { AuthGuard } from './Service/auth.guard';

const routes: Routes = [
  {path:"",redirectTo:"register",pathMatch:"full"},
   {path:"register",component:RegistrationComponent},
{path:"updated-home",canActivate:[AuthGuard],component:UpdatedHomeComponent},
{path:"orders",canActivate:[AuthGuard],component:OrdersComponent},
{path:"profile",canActivate:[AuthGuard],component:ProfileComponent},
{path:"users",canActivate:[AuthGuard],component:UsersComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
