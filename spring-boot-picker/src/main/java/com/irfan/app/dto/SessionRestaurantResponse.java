package com.irfan.app.dto;

public class SessionRestaurantResponse {

    private Long sessionId;


    public SessionRestaurantResponse() {
    }

    public SessionRestaurantResponse(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}
