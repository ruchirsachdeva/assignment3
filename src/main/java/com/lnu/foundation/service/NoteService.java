package com.lnu.foundation.service;

import com.lnu.foundation.model.Note;
import com.lnu.foundation.model.TestSession;
import com.lnu.foundation.model.User;
import com.lnu.foundation.repository.NoteRepository;
import com.lnu.foundation.repository.TestSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by rucsac on 04/11/2018.
 */
@Service
@Transactional
public class NoteService {


    @Autowired
    private TestSessionRepository sessionRepo;

    @Autowired
    private NoteRepository noterepo;

    public Collection<Note> addNote(Long testSessionId, Note note, User user) {
        TestSession session = sessionRepo.findOne(testSessionId);
        note.setMedUser(user);
        note.setTestSession(session);
        noterepo.save(note);
        return noterepo.findByTestSession(session);
    }
}
