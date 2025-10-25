import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-session',
  templateUrl: './search-session.component.html',
  styleUrls: ['./search-session.component.css']
})
export class SearchSessionComponent implements OnInit {

  constructor(private router: Router) { }
  
    ngOnInit(): void {
    }
  
    doSearch(value: string) {
      this.router.navigateByUrl(`/search-sessions/${value}`);
    }
}
