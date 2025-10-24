package com.irfan.app.dto;

public class SessionRestaurantDTO {

    private Long sessionId;
    private String restaurantName;
    private String restaurantDescription;
    private Boolean active;

    public SessionRestaurantDTO() {
    }

    public SessionRestaurantDTO(Long sessionId, String restaurantName, String restaurantDescription, Boolean active) {
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
