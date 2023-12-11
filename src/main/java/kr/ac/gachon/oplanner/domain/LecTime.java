package kr.ac.gachon.oplanner.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import kr.ac.gachon.oplanner.domain.enums.DayEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "lec_time")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LecTime implements Cloneable {
    @Id
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "lec_num", referencedColumnName = "lec_num")
    private Lecture lecture;

    /**
     * 1: 1,2       A: 2,3
     * 2: 3
     * 3: 4         B: 4,5
     * 4: 5,6
     * 5: 7         C: 7,8
     * 6: 8,9       D: 9,10
     * 7: 10        E: 11,12
     * 8: 11
     * 9: 12,13
     * 10: 14
     * 11: 15
     * 12:
     * 13:
     */
    @Id
    @Min(1)
    @Max(17)
    private Integer time;
    @Id
    @Enumerated(EnumType.STRING)
    private DayEnum day;

    @Override
    public LecTime clone() {
        try {
            return (LecTime) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
