export class SessionRestaurant {

    id!: string;
    restaurantName!: string;
    restaurantDescription!: string;
    active!: boolean;
    dateCreated!: Date;
    lastUpdated!: Date;

    constructor(
        id?: string,
        restaurantName?: string,
        restaurantDescription?: string,
    ) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.restaurantDescription = restaurantDescription;
    }
}
