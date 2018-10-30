package com.lnu.foundation.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Therapy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long therapyId;
  @ManyToOne
  @JoinColumn(name = "patient_id")
  private User patient;
  @ManyToOne
  @JoinColumn(name = "med_id")
  private User med;
  @ManyToOne
  @JoinColumn(name = "therapylist_id")
  private TherapyList therapylist;

}
