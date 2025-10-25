import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Session } from 'src/app/common/session';
import { SessionService } from 'src/app/services/session.service';
import { RestaurantFormValidators } from 'src/app/validators/restaurant-form-validators';

@Component({
  selector: 'app-create-session',
  templateUrl: './create-session.component.html',
  styleUrls: ['./create-session.component.css'],
})
export class CreateSessionComponent implements OnInit {
  createSessionFormGroup!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private sessionService: SessionService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.createSessionFormGroup = this.formBuilder.group({
      session: this.formBuilder.group({
        name: new FormControl('', [
          Validators.required,
          Validators.minLength(2),
          RestaurantFormValidators.notOnlyWhitespace,
        ]),
        description: new FormControl('', [
          Validators.required,
          Validators.minLength(2),
          RestaurantFormValidators.notOnlyWhitespace,
        ]),
      }),
    });
  }

  get sessionName() {
    return this.createSessionFormGroup.get('session.name');
  }
  get sessionDescription() {
    return this.createSessionFormGroup.get('session.description');
  }

  onSubmit() {
    if (this.createSessionFormGroup.invalid) {
      this.createSessionFormGroup.markAllAsTouched();
    }

    // set up session obj
    let session = new Session();

    // populate session obj
    session = this.createSessionFormGroup.controls['session'].value;

    // call the saveRestaurant service to persist to backend
    this.sessionService.createSession(session).subscribe({
      next: (response) => {
        alert(`Session has been saved.\n`);

        // reset cart
        this.resetSessionForm();
      },
      error: (err) => {
        alert(`There was an error: ${err.message}`);
      },
    });
  }

  resetSessionForm() {
    // reset the form
    this.createSessionFormGroup.reset();

    // navigate back to the list of sessions page
    this.router.navigateByUrl(`/sessions`);
  }
}
