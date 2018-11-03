package com.lnu.foundation.repository;

import com.lnu.foundation.model.Note;
import com.lnu.foundation.model.TestSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

/**
 * Created by rucsac on 10/10/2018.
 */
@RepositoryRestResource
//@CrossOrigin(origins = "http://localhost:4200")
public interface NoteRepository extends JpaRepository<Note, Long> {
    Collection<Note> findByTestSession(TestSession s);
}
