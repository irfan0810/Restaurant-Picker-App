import { TestBed } from '@angular/core/testing';

import { SessionRestaurantService } from './session-restaurant.service';

describe('SessionRestaurantService', () => {
  let service: SessionRestaurantService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionRestaurantService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
