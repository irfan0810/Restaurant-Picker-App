package com.irfan.app.service;

import com.irfan.app.dao.SessionRepository;
import com.irfan.app.dao.UserRepository;
import com.irfan.app.dto.SessionDTO;
import com.irfan.app.dto.SessionResponse;
import com.irfan.app.entity.Session;
import com.irfan.app.entity.SessionRestaurant;
import com.irfan.app.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SessionServiceImpl implements SessionService {

    private SessionRepository sessionRepository;
    private UserRepository userRepository;

    public SessionServiceImpl(SessionRepository sessionRepository,
                              UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    private static final String IMAGE_PLACEHOLDER = "assets/images/fish.png";

    @Override
    public Page<SessionResponse> listUserSessions(String userName, Pageable pageable) {
        User user = getUser(userName);

        return sessionRepository.findByUserId(user.getId(), pageable)
                .map(this::toResponse);
    }

    @Override
    public List<SessionResponse> searchSessionsByUserAndName(String userName, String keyword) {
        User user = getUser(userName);
        String term = (keyword == null) ? "" : keyword;

        return sessionRepository.findByUser_IdAndNameContainingIgnoreCase(user.getId(), term)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public SessionResponse createSession(String userName, SessionDTO sessionDTO) {
        User user = getUser(userName);

        Session session = new Session();
        // populate session with its details
        session.setName(sessionDTO.getName());
        session.setDescription(sessionDTO.getDescription());
        session.setUser(user);
        session.setActive(true);
        session.setImageUrl(IMAGE_PLACEHOLDER);

        // generate link
        session.setLink(generateSessionMagicLink());

        // save to the database
        Session saved = sessionRepository.save(session);

        // return the response
        return new SessionResponse(
                saved.getId(),
                saved.getLink());
    }

    @Override
    @Transactional
    public SessionResponse inactivateSession(String userName, Long sessionId) {
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

        /// find the session
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));

        // mark inactive and clear its link
        session.setActive(false);
        session.setLink(null);

        // pick a random restaurant from list of restaurants
        Set<SessionRestaurant> restaurants = session.getSessionRestaurants();
        if (restaurants != null && !restaurants.isEmpty()) {
            List<SessionRestaurant> restaurantList = new ArrayList<>(restaurants);
            Random random = new Random();
            SessionRestaurant chosen = restaurantList.get(random.nextInt(restaurantList.size()));

            // update the session result
            session.setResult(chosen.getRestaurantName());
        } else {
            session.setResult("No restaurants found");
        }

        // save changes
        Session saved = sessionRepository.save(session);

        // return response
        return new SessionResponse(saved.getId());
    }

    @Override
    public SessionResponse resolveByLink(String link) {
        Session session = sessionRepository.findByLink(link)
                .orElseThrow(() -> new IllegalArgumentException("Invalid link"));
        
        if (!session.isActive() || session.getLink() == null) {
            throw new IllegalStateException("Session is inactive or link expired");
        }

        return toResponse(session);
    }

    private String generateSessionMagicLink() {
        // generate random UUID number for magic link
        return UUID.randomUUID().toString();
    }

    private SessionResponse toResponse(Session s) {
        return new SessionResponse(
                s.getId(),
                s.getLink(),
                s.getName(),
                s.getDescription(),
                s.getImageUrl(),
                s.isActive(),
                s.getResult()
        );
    }

    private User getUser(String userName) {
        if (userName == null) throw new IllegalArgumentException("userName is required");

        User user = userRepository.findUserByUsername(userName);
        if (user == null) throw new IllegalArgumentException("User not found: ");
        return user;
    }
}
