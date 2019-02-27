package com.lnu.foundation.repository;

import com.lnu.foundation.model.TestSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;

/**
 * Created by rucsac on 15/10/2018.
 */
@RepositoryRestResource
public interface TestSessionRepository extends JpaRepository<TestSession, Long> {

    Collection<TestSession> findByTest_Therapy_Med_Username(String username);

    Collection<TestSession> findByTest_Therapy_Patient_Username(String username);

    @RestResource(path = "byMedId", rel = "byMedId")
    Collection<TestSession> findByTest_Therapy_Med_UserId(@Param("id") Long id);


    @RestResource(path = "byPatientId", rel = "byPatientId")
    Collection<TestSession> findByTest_Therapy_Patient_UserId(@Param("id") Long id);


    @RestResource(path = "byTherapyId", rel = "byTherapyId")
    Collection<TestSession> findByTest_Therapy_TherapyId(@Param("id") Long id);
}
