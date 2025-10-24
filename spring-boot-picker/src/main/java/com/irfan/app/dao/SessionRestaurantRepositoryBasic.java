package com.irfan.app.dao;

import com.irfan.app.entity.SessionRestaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

//@CrossOrigin("http://localhost:4200")
//@RepositoryRestResource(collectionResourceRel = "sessionRestaurant", path="session-restaurant")
public interface SessionRestaurantRepositoryBasic extends JpaRepository<SessionRestaurant,Long> {

    Page<SessionRestaurant> findBySession_Id(@Param("sessionId") Long sessionId, Pageable pageable);

}
