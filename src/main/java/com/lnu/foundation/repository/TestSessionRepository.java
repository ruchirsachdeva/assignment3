package com.lnu.foundation.repository;

import com.lnu.foundation.model.TestSession;
import com.lnu.foundation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

/**
 * Created by rucsac on 15/10/2018.
 */
@RepositoryRestResource
public interface TestSessionRepository extends JpaRepository<TestSession, Long> {

    Collection<TestSession> findByTest_Therapy_Med(User med);

    Collection<TestSession> findByTest_Therapy_Med_Username(String username);

    Collection<TestSession> findByTest_Therapy_Patient_Username(String username);
}
