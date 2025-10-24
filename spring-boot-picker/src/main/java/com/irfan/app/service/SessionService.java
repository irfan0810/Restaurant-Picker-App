package com.irfan.app.service;

import com.irfan.app.dto.SessionDTO;
import com.irfan.app.dto.SessionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SessionService {

    Page<SessionResponse> listUserSessions(String userName, Pageable pageable);
    List<SessionResponse> searchSessionsByUserAndName(String userName, String keyword);

    SessionResponse createSession(String userName, SessionDTO  sessionDTO);
    SessionResponse inactivateSession(String userName, Long sessionId);
    SessionResponse resolveByLink(String link);
}
