package com.lnu.foundation.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;
    private String note;
    // private long testSessionIDtestSession;
    @ManyToOne
    @JoinColumn(name = "test_session_id")
    private TestSession testSession;
    //private long userIDmed;
    @ManyToOne
    @JoinColumn(name = "med_id")
    private User medUser;


}
