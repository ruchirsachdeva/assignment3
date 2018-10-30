package com.lnu.foundation.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lnu.foundation.repository.DataRepository;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TestSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long testSessionId;
  private long testType;

  @ManyToOne
  @JoinColumn(name = "test_id")
  private Test test;
  private String dataUrl;

  @JsonProperty
  public List<Data> getData() {
    return DataRepository.loadData(dataUrl);
  }


}
