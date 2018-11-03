package com.lnu.foundation.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;
    private String note;
    // private long testSessionIDtestSession;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_session_id")
    @JsonIgnore
    private TestSession testSession;
    //private long userIDmed;
    @ManyToOne
    @JoinColumn(name = "med_id")
    private User medUser;

    @JsonProperty
    public Long getTestSessionId() {
        return testSession.getTestSessionId();
    }


}
