import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionRestaurant } from 'src/app/common/session-restaurant';
import { SaveRestaurantService } from 'src/app/services/save-restaurant.service';
import { RestaurantFormValidators } from 'src/app/validators/restaurant-form-validators';

@Component({
  selector: 'app-create-restaurant',
  templateUrl: './create-restaurant.component.html',
  styleUrls: ['./create-restaurant.component.css']
})
export class CreateRestaurantComponent implements OnInit {

  createRestaurantFormGroup!: FormGroup;
  sessionIdBtn: number;

  constructor(private formBuilder: FormBuilder,
              private saveRestaurantService: SaveRestaurantService,
              private route: ActivatedRoute,
              private router: Router
  ) { }

  ngOnInit(): void {
    this.sessionIdBtn = +this.route.snapshot.paramMap.get('sessionId')!;
    
    this.createRestaurantFormGroup = this.formBuilder.group({
      restaurant: this.formBuilder.group({
          restaurantName: new FormControl('', 
            [Validators.required, 
             Validators.minLength(2),
             RestaurantFormValidators.notOnlyWhitespace]),
          restaurantDescription: new FormControl('', 
            [Validators.required, Validators.minLength(2),
             RestaurantFormValidators.notOnlyWhitespace]),
      })
    });
  }

  onSubmit() {
    if (this.createRestaurantFormGroup.invalid) {
      this.createRestaurantFormGroup.markAllAsTouched();
    }

    // set up restaurant
    let restaurant = new SessionRestaurant();

    // populate sessionRestaurant obj - restaurant
    restaurant = this.createRestaurantFormGroup.controls['restaurant'].value;

    //get the parsed sessionId
    const sessionId: number = +this.route.snapshot.paramMap.get('sessionId')!;

    // call the saveRestaurant service to persist to backend
    this.saveRestaurantService.addRestaurant(restaurant, sessionId).subscribe({
        next: response => {
          alert(`Restaurant has been saved.\n`);

          // reset cart
          this.resetRestaurantForm();

        },
        error: err => {
          alert(`There was an error: ${err.message}`);
        }
      }
    );

  }

  resetRestaurantForm() {
    const sessionId: number = +this.route.snapshot.paramMap.get('sessionId')!;

    // reset the form
    this.createRestaurantFormGroup.reset();

    // navigate back to the list of restaurant page
    this.router.navigateByUrl(`/session/${sessionId}`);
  }

  get restaurantName() {
    return this.createRestaurantFormGroup.get('restaurant.restaurantName');
  }
  get restaurantDescription() {
    return this.createRestaurantFormGroup.get('restaurant.restaurantDescription');
  }

}
