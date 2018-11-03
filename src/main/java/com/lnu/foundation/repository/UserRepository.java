package com.lnu.foundation.repository;

import com.lnu.foundation.model.Role;
import com.lnu.foundation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

/**
 * Created by rucsac on 10/10/2018.
 */
@RepositoryRestResource
//@CrossOrigin(origins = {"http://localhost:4200", "https://lit-beach-29911.herokuapp.com"})
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(Role role);
    List<User> findByRole_Name(String role);
    Optional<User> findByUsername(String email);
}
