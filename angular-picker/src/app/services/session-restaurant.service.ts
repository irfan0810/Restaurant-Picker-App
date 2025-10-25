import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SessionRestaurant } from '../common/session-restaurant';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SessionRestaurantService {
  private baseUrl = 'http://localhost:8080/api/session-restaurant';
  private sessionsApi = 'http://localhost:8080/api/sessions';    

  private projUrl =
    'http://localhost:8080/api/session-restaurant?projection=withSession';

  constructor(private httpClient: HttpClient) {}

  getSessionRestaurantList(): Observable<SessionRestaurant[]> {
    return this.httpClient
      .get<GetResponse>(this.baseUrl)
      .pipe(map((response) => response._embedded.sessionRestaurant));
  }

  getSessionRestaurantBySessionId(
    theSessionId: number
  ): Observable<SessionRestaurant[]> {
    // need to build URL based on session id to retrieve restaurants linked to selected session
    const url = `${this.baseUrl}/search/findBySession_Id?sessionId=${theSessionId}`;

    return this.httpClient
      .get<GetResponse>(url)
      .pipe(map((response) => response._embedded.sessionRestaurant));
  }

  getSessionRestaurantBySessionIdPaginate(
    thePage: number,
    thePageSize: number,
    theSessionId: number
  ): Observable<GetResponseNew> {
    // need to build URL based on session id to retrieve restaurants linked to selected session
    const url =
      `${this.sessionsApi}/search/getSessionRestaurants?sessionId=${theSessionId}` +
      `&page=${thePage}&size=${thePageSize}`;

    return this.httpClient.get<GetResponseNew>(url);
  }

  deleteSessionRestaurant(restaurantId: number): Observable<void> {
    const url = `${this.sessionsApi}/restaurants/${restaurantId}`;
    return this.httpClient.delete<void>(url);
  }
  
}

interface GetResponse {
  _embedded: {
    sessionRestaurant: SessionRestaurant[];
  },
  page: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  }
}

interface GetResponseNew {
  
  content: SessionRestaurant[],
  size: number,
  totalElements: number,
  totalPages: number,
  number: number

}
