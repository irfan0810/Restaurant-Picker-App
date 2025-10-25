import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SessionRestaurant } from 'src/app/common/session-restaurant';
import { SessionRestaurantService } from 'src/app/services/session-restaurant.service';

@Component({
  selector: 'app-session-restaurant-list',
  templateUrl: './session-restaurant-list-grid.component.html',
  styleUrls: ['./session-restaurant-list.component.css']
})
export class SessionRestaurantListComponent implements OnInit {

  sessionRestaurants: SessionRestaurant[] = [];
  // to handle view routing :id
  viewMode: boolean = false;

  // new properties for pagination
  thePageNumber: number = 1;
  thePageSize: number = 5;
  theTotalElements: number = 0;

  sessionId!: number;

  constructor(
    private sessionRestaurantService: SessionRestaurantService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {

    this.sessionId = Number(this.route.snapshot.paramMap.get('id'));

    this.route.paramMap.subscribe(() => {
      this.listSessionRestaurants();
    })
  }
  listSessionRestaurants() {

    this.viewMode = this.route.snapshot.paramMap.has('id');

    if (this.viewMode) {
      this.handleListSessionRestaurantsBySessionId();
    } else {
        this.handleListSessionRestaurants();
    }
    
  }
  handleListSessionRestaurantsBySessionId() {
     const theSessionId: number = +this.route.snapshot.paramMap.get('id')!;

     this.sessionRestaurantService.getSessionRestaurantBySessionIdPaginate(
        this.thePageNumber - 1,
        this.thePageSize,
        theSessionId
      )
      .subscribe((data) => {
        this.sessionRestaurants = data.content;
        this.thePageNumber = data.number + 1;
        this.thePageSize = data.size;
        this.theTotalElements = data.totalElements;
      });
  }

  handleListSessionRestaurants(){
    this.sessionRestaurantService.getSessionRestaurantList().subscribe(
      data => {
        this.sessionRestaurants = data;
      }
    )
  }

  onDelete(restaurantId: string){
    if (!confirm('Delete this restaurant?')) { return; }

    const theRestaurantId: number = +restaurantId;

    this.sessionRestaurantService.deleteSessionRestaurant(theRestaurantId).subscribe({
      next: () => {
        // refresh list
        this.listSessionRestaurants();
      },
      error: (err) => console.error('Delete failed', err)
    });    
  }

  updatePageSize(pageSize: string) {
    this.thePageSize = +pageSize;
    this.thePageNumber = 1;
    this.listSessionRestaurants();
  }

}
