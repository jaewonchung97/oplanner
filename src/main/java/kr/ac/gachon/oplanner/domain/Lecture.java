package kr.ac.gachon.oplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import kr.ac.gachon.oplanner.domain.enums.LecClassification;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lecture {
    @Id
    @JsonProperty("subject_cd")
    @Column(name = "lec_num")
    private String lecNum;
    @JsonProperty("subject_nm_kor")
    private String lecName;
    @JsonProperty("prof_nm")
    private String prof;
    private int credit;
    @Enumerated(EnumType.STRING)
    private LecClassification classification;
}
