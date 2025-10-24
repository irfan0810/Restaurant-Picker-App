package com.irfan.app.dao;

import org.springframework.stereotype.Repository;

//@CrossOrigin("http://localhost:4200")
//@RepositoryRestResource(collectionResourceRel = "session", path="session")
@Repository
public interface SessionRepository extends SessionRepositoryBasic, SessionRepositoryCustom {
}
