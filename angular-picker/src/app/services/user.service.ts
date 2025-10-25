import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private host = 'http://localhost:8080/user';

  constructor(private http: HttpClient) { }

  
}
