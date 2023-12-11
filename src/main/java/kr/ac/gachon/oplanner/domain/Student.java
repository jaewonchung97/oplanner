package kr.ac.gachon.oplanner.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Student {

    @Id
    @Column(name = "st_num")
    private Integer stNum;
    private String stName;
    private Integer essCredits;
    private Integer optCredits;
}
