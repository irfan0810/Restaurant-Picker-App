package com.irfan.app.dto;

public class RestaurantResponse {

    private Long id;
    private String restaurantName;
    private String restaurantDescription;


    public RestaurantResponse(Long id, String restaurantName, String restaurantDescription) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.restaurantDescription = restaurantDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantDescription() {
        return restaurantDescription;
    }

    public void setRestaurantDescription(String restaurantDescription) {
        this.restaurantDescription = restaurantDescription;
    }
}
