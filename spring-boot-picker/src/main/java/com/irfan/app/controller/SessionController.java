package com.irfan.app.controller;

import com.irfan.app.dto.*;
import com.irfan.app.service.SessionRestaurantService;
import com.irfan.app.service.SessionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;
    private final SessionRestaurantService sessionRestaurantService;

    public SessionController(SessionService sessionService,
                             SessionRestaurantService sessionRestaurantService) {
        this.sessionService = sessionService;
        this.sessionRestaurantService = sessionRestaurantService;
    }

    // GET /api/sessions/search/getSessions?page=0&size=10
    @GetMapping("/search/getSessions")
    public ResponseEntity<Page<SessionResponse>> findByUserId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreated"));
        Page<SessionResponse> sessions = sessionService.listUserSessions(principal.getName(), pageable);
        return ResponseEntity.ok(sessions);
    }

    // GET /api/sessions/search/getSessionsNameContaining?name=lunch
    @GetMapping("/search/getSessionsNameContaining")
    public ResponseEntity<List<SessionResponse>> findByUserIdAndNameContaining(
            @RequestParam("name") String name,
            Principal principal) {

        return ResponseEntity.ok(sessionService.searchSessionsByUserAndName(principal.getName(), name));
    }

    // GET /api/sessions/search/getSessions?page=0&size=10
    @GetMapping("/search/getSessionRestaurants")
    public ResponseEntity<Page<RestaurantResponse>> findRestaurantBySessionId(
            @RequestParam Long sessionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {

        String username = (principal != null) ? principal.getName() : null;

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateCreated"));

        Page<RestaurantResponse> sessions = sessionRestaurantService.listSessionRestaurants(username, sessionId, pageable);
        return ResponseEntity.ok(sessions);
    }

    // POST /api/sessions/create
    @PostMapping("/create")
    public ResponseEntity<SessionResponse> createSession(@RequestBody SessionDTO dto,
                                                         Principal principal) {

        return ResponseEntity.ok(sessionService.createSession(principal.getName(), dto));
    }

    // POST /api/sessions/add-restaurant/{sessionId}
    @PostMapping("/add-restaurant/{sessionId}")
    public ResponseEntity<SessionRestaurantResponse> addRestaurant(
            @PathVariable Long sessionId,
            @RequestBody SessionRestaurantDTO reqDTO,
            Principal principal) {

        String username = (principal != null) ? principal.getName() : null;

        SessionRestaurantResponse resp =
                sessionRestaurantService.addRestaurant(username, sessionId, reqDTO);

        return ResponseEntity.ok(resp);
    }

    // PATCH /api/sessions/inactivate/{id}
    @PatchMapping("/inactivate/{id}")
    public ResponseEntity<SessionResponse> inactivate(@PathVariable Long id,
                                                      Principal principal) {

        return ResponseEntity.ok(sessionService.inactivateSession(principal.getName(), id));
    }

    // DELETE /api/sessions/restaurants/{restaurantId}
    @DeleteMapping("/restaurants/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long restaurantId,
                                                 Principal principal) {

        sessionRestaurantService.deleteRestaurant(principal.getName(), restaurantId);
        return ResponseEntity.noContent().build();
    }

}
