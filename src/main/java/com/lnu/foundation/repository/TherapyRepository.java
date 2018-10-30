package com.lnu.foundation.repository;

import com.lnu.foundation.model.Therapy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by rucsac on 15/10/2018.
 */
@RepositoryRestResource
public interface TherapyRepository extends JpaRepository<Therapy, Long> {

    List<Therapy> findByMed_Username(String med);
    List<Therapy> findByPatient_Username(String patient);
}
