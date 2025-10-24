package com.irfan.app.service;

import com.irfan.app.dto.RestaurantResponse;
import com.irfan.app.dto.SessionRestaurantDTO;
import com.irfan.app.dto.SessionRestaurantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SessionRestaurantService {

    Page<RestaurantResponse> listSessionRestaurants(String userName, Long sessionId, Pageable pageable);
    SessionRestaurantResponse addRestaurant(String userName, Long sessionId, SessionRestaurantDTO sessionRestaurantDTO);
    void deleteRestaurant(String userName, Long restaurantId);
}
