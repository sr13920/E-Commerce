import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RestApiService } from './Service/rest-api.service';
import { HttpClientModule } from '@angular/common/http';
import { RegistrationComponent } from './Components/registration/registration.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTabsModule } from '@angular/material/tabs';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { UpdatedHomeComponent} from './Components/updated-home/updated-home.component';
import { MatCardModule } from '@angular/material/card';
import { OrdersComponent } from './Components/orders/orders.component';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ProfileComponent, updatedUserDialog, updatedAddEditProductDialog } from './Components/profile/profile.component';
import { SwiperModule } from 'swiper/angular';
import { UsersComponent } from './Components/users/users.component';
import { MatButtonModule } from '@angular/material/button';


@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    // AddEditProductDialog,
    UpdatedHomeComponent,
    updatedAddEditProductDialog,
    OrdersComponent,
    ProfileComponent,
    updatedUserDialog,
    UsersComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatTabsModule,
    NgbModule,
    MatTableModule,
    MatIconModule,
    MatDialogModule,
    MatCardModule,
    MatExpansionModule,
    MatListModule,
    MatToolbarModule,
    SwiperModule,
    MatButtonModule
  ],
  providers: [RestApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
