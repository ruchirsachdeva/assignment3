package com.lnu.foundation.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lnu.foundation.repository.DataRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TestSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testSessionId;
    private long testType;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
    private String dataUrl;

    @OneToMany(mappedBy = "testSession", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();
    @JsonProperty
    public List<Data> getData() {
        return DataRepository.loadData(dataUrl);
    }


    public void addNote(Note note) {
        this.notes.add(note);
    }
}
