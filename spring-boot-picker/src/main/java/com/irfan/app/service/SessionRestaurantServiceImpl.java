package com.irfan.app.service;

import com.irfan.app.dao.SessionRepository;
import com.irfan.app.dao.SessionRestaurantRepository;
import com.irfan.app.dao.UserRepository;
import com.irfan.app.dto.RestaurantResponse;
import com.irfan.app.dto.SessionRestaurantDTO;
import com.irfan.app.dto.SessionRestaurantResponse;
import com.irfan.app.entity.Session;
import com.irfan.app.entity.SessionRestaurant;
import com.irfan.app.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SessionRestaurantServiceImpl implements SessionRestaurantService {

    private SessionRepository sessionRepository;
    private SessionRestaurantRepository sessionRestaurantRepository;
    private UserRepository userRepository;

    public SessionRestaurantServiceImpl(SessionRepository sessionRepository, SessionRestaurantRepository sessionRestaurantRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.sessionRestaurantRepository = sessionRestaurantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<RestaurantResponse> listSessionRestaurants(String userName, Long sessionId, Pageable pageable) {
        // if a user is logged in, ensure they're tagged to this session
        if (userName != null && !userName.isBlank()) {
            User user = getUser(userName);

            boolean allowed = sessionRepository
                    .findByIdAndUserId(sessionId, user.getId())
                    .isPresent();

            if (!allowed) {
                throw new IllegalArgumentException("User not allowed to inactivate this Session");
            }
        }

        return sessionRestaurantRepository.findBySession_Id(sessionId, pageable)
                .map(this::toResponse);
    }

    @Override
    @Transactional
    public SessionRestaurantResponse addRestaurant(String username, Long sessionId, SessionRestaurantDTO sessionRestaurantDTO) {

        // if a user is logged in, ensure they're tagged to this session
        if (username != null && !username.isBlank()) {
            User user = getUser(username);

            boolean allowed = sessionRepository
                    .findByIdAndUserId(sessionId, user.getId())
                    .isPresent();

            if (!allowed) {
                throw new IllegalArgumentException("User not allowed in this Session");
            }
        }

        // tagged user and guests, continue to fetch parent session
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));

        // check if session is active, else continue flow
        if (!session.isActive()) {
            throw new IllegalStateException(
                    "Cannot add restaurant to an inactive session (ID: " + sessionId + ")."
            );
        }

        // build restaurant object
        SessionRestaurant sessionRestaurant = new SessionRestaurant();
        sessionRestaurant.setSession(session);
        sessionRestaurant.setRestaurantName(sessionRestaurantDTO.getRestaurantName());
        sessionRestaurant.setRestaurantDescription(sessionRestaurantDTO.getRestaurantDescription());
        sessionRestaurant.setActive(sessionRestaurantDTO.isActive() != null ? sessionRestaurantDTO.isActive() : true);

        // save to the database
        SessionRestaurant saved = sessionRestaurantRepository.save(sessionRestaurant);

        // return the response
        return new SessionRestaurantResponse(saved.getSession().getId());
    }

    @Override
    @Transactional
    public void deleteRestaurant(String userName, Long restaurantId) {
        // get the restaurant using restaurantId
        SessionRestaurant restaurant = sessionRestaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("SessionRestaurant not found: " + restaurantId));

        // if its there then get the linked sessionId
        Long sessionId = restaurant.getSession().getId();

        // if a user is not found
        if (userName == null || userName.isBlank()) throw new IllegalArgumentException("User is not found");
        // else get the user
        User user = getUser(userName);
        // check if user is tagged to the session
        boolean allowed = sessionRepository
                .findByIdAndUserId(sessionId, user.getId())
                .isPresent();

        if (!allowed) {
            throw new IllegalArgumentException("User not allowed in this Session");
        }

        sessionRestaurantRepository.deleteById(restaurantId);
    }

    private User getUser(String userName) {
        if (userName == null) throw new IllegalArgumentException("userName is required");

        User user = userRepository.findUserByUsername(userName);
        if (user == null) throw new IllegalArgumentException("User not found: ");
        return user;
    }

    private RestaurantResponse toResponse(SessionRestaurant s) {
        return new RestaurantResponse(
                s.getId(),
                s.getRestaurantName(),
                s.getRestaurantDescription()
        );
    }
}
