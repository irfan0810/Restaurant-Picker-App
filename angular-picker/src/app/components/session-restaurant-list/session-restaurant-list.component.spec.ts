import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionRestaurantListComponent } from './session-restaurant-list.component';

describe('SessionRestaurantListComponent', () => {
  let component: SessionRestaurantListComponent;
  let fixture: ComponentFixture<SessionRestaurantListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SessionRestaurantListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SessionRestaurantListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
