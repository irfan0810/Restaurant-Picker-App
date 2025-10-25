import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Session } from 'src/app/common/session';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-session-list',
  templateUrl: './session-list-grid.component.html',
  styleUrls: ['./session-list.component.css'],
})
export class SessionListComponent implements OnInit {
  sessions: Session[] = [];

  // to handle search routing :keyword
  searchMode: boolean = false;

  // new properties for pagination
  thePageNumber: number = 1;
  thePageSize: number = 10;
  theTotalElements: number = 0;

  constructor(
    private sessionService: SessionService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.listSessions();
    });
  }

  listSessions() {
    this.searchMode = this.route.snapshot.paramMap.has('keyword');

    if (this.searchMode) {
      this.handleSearchSessionsByKeyword();
    } else {
      this.handleListSessions();
    }
  }

  handleSearchSessionsByKeyword() {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;

    // now search for the sessions using keyword
    this.sessionService
      .searchSessionsByKeyword(theKeyword)
      .subscribe((data) => {
        this.sessions = data;
      });
  }

  handleListSessions() {
    this.sessionService
      .getSessionListByUserIdPaginate(this.thePageNumber - 1, this.thePageSize)
      .subscribe((data) => {
        this.sessions = data.content;
        this.thePageNumber = data.number + 1;
        this.thePageSize = data.size;
        this.theTotalElements = data.totalElements;
      });
  }

  updatePageSize(pageSize: string) {
    this.thePageSize = +pageSize;
    this.thePageNumber = 1;
    this.listSessions();
  }

  onClickSession(sessionId: string) {
    const theId: number = +sessionId;

    this.sessionService.inactivateSession(theId).subscribe({
      next: (updated) => {
        // refresh list from server
        this.listSessions();
      },
      error: (err) => {
        console.error('Failed to close session', err);
      },
    });
  }


}
