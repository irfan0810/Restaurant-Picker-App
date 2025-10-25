import { TestBed } from '@angular/core/testing';

import { SaveRestaurantService } from './save-restaurant.service';

describe('SaveRestaurantService', () => {
  let service: SaveRestaurantService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SaveRestaurantService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
