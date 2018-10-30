package com.lnu.foundation.repository;

import com.lnu.foundation.model.Test;
import com.lnu.foundation.model.Therapy;
import com.lnu.foundation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by rucsac on 15/10/2018.
 */
@RepositoryRestResource
public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findByTherapy(Therapy therapy);
    List<Test> findByTherapy_Med(User med);
}
