import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Session } from '../common/session';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private baseUrl = 'http://localhost:8080/api/sessions'

  constructor(private httpClient: HttpClient) { }

  getSessionList(): Observable<Session[]> {
    return this.httpClient
      .get<GetResponse>(this.baseUrl)
      .pipe(map((response) => response._embedded.session));
  }

  searchSessionsByKeyword(theKeyword: string): Observable<Session[]> {
      // need to build URL based on the keyword 
      const searchUrl = `${this.baseUrl}/search/getSessionsNameContaining?name=${theKeyword}`;
  
      return this.httpClient.get<Session[]>(searchUrl);
    }

    getSessionListByUserIdPaginate(
      thePage:number,
      thePageSize:number): Observable<GetResponseNew> {

        const productUrl = `${this.baseUrl}/search/getSessions?`+ `&page=${thePage}&size=${thePageSize}`;

       return this.httpClient.get<GetResponseNew>(productUrl);
    }

    inactivateSession(sessionId: number): Observable<Session> {

      return this.httpClient.patch<Session>(`${this.baseUrl}/inactivate/${sessionId}`, {});
    }

    createSession(session: Session) {
      const createUrl = `${this.baseUrl}/create`;
     
      return this.httpClient.post<Session>(createUrl, session);
    } 

}

interface GetResponse {
  _embedded: {
    session: Session[];
  },
  page: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  }
}

interface GetResponseNew {
  content: Session[],
  size: number,
  totalElements: number,
  totalPages: number,
  number: number
}
