package com.irfan.app.dao;

import com.irfan.app.entity.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//@CrossOrigin("http://localhost:4200")
//@RepositoryRestResource(collectionResourceRel = "session", path="session")
public interface SessionRepositoryBasic extends JpaRepository<Session,Long> {
    Page<Session> findByUserId(@Param("userId") Long id, Pageable pageable);
    Page<Session> findByNameContaining(@Param("name") String name, Pageable pageable);
    List<Session> findByUser_IdAndNameContainingIgnoreCase(@Param("userId") Long userId, @Param("name") String name);

    Page<Session> findByNameContainingAndUserId(@Param("name") String name, @Param("userId") Long id, Pageable pageable);

    Optional<Session> findByLink(String link);

    @Query("SELECT s FROM Session s WHERE s.id = :sessionId AND s.user.id = :userId")
    Optional<Session> findByIdAndUserId(@Param("sessionId") Long sessionId,
                                        @Param("userId") Long userId);

    @Query("update Session s set s.active = false where s.id = :id")
    int inactivateById(@Param("id") Long id);

}
