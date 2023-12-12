package kr.ac.gachon.oplanner.domain;

import jakarta.persistence.*;
import kr.ac.gachon.oplanner.domain.enums.DayEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Time;

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
    @Id
    private Time startTime;
    @Id
    private Time endTime;
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
