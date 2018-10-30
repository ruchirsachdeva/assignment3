package com.lnu.foundation.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TherapyList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long therapyListId;
  private String name;
  @ManyToOne
  @JoinColumn(name = "medicine_id")
  private Medicine medicine;
  private String dosage;


}
