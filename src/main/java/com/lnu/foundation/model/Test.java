package com.lnu.foundation.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Test {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long testId;
  private Date dateTime;
 // private long therapyIDtherapy;
  @ManyToOne
  @JoinColumn(name = "therapy_id")
  private Therapy therapy;

}
