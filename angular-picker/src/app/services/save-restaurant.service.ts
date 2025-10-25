import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SessionRestaurant } from '../common/session-restaurant';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SaveRestaurantService {

  private baseUrl = 'http://localhost:8080/api/sessions'

  constructor(private httpClient: HttpClient) { }

  addRestaurant(restaurant: SessionRestaurant, sessionId: number): Observable<any> {
    const addUrl = `${this.baseUrl}/add-restaurant/${sessionId}`;

    return this.httpClient.post<SessionRestaurant>(addUrl, restaurant);    
  }
  
}
