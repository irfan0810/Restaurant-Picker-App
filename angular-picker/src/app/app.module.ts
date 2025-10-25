import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { SessionListComponent } from './components/session-list/session-list.component';
import { SessionService } from './services/session.service';
import { SessionRestaurantListComponent } from './components/session-restaurant-list/session-restaurant-list.component';
import { SessionRestaurantService } from './services/session-restaurant.service';

import { Routes, RouterModule } from '@angular/router';
import { SearchSessionComponent } from './components/search-session/search-session.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CreateRestaurantComponent } from './components/create-restaurant/create-restaurant.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CreateSessionComponent } from './components/create-session/create-session.component';
import { AuthenticationService } from './services/authentication.service';
import { AuthInterceptor } from './interceptor/auth.interceptor';
import { AuthenticationGuard } from './guard/authentication.guard';
import { LoginComponent } from './components/login/login.component';

const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'session/:id', component: SessionRestaurantListComponent},
  {path:'search-sessions/:keyword', component: SessionListComponent, canActivate: [AuthenticationGuard]},
  {path:'sessions', component: SessionListComponent, canActivate: [AuthenticationGuard]},
  {path:'create-session', component: CreateSessionComponent, canActivate: [AuthenticationGuard]},
  {path:'create-restaurant/:sessionId', component: CreateRestaurantComponent},
  {path:'',redirectTo:'/login',pathMatch: 'full'},
  {path:'**',redirectTo:'/login',pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    SessionListComponent,
    SessionRestaurantListComponent,
    SearchSessionComponent,
    CreateRestaurantComponent,
    CreateSessionComponent,
    LoginComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule,
    FormsModule
],
  providers: [
    SessionService,
    SessionRestaurantService,
    AuthenticationGuard,
    AuthenticationService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
