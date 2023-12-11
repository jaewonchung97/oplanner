package kr.ac.gachon.oplanner.repository;

import kr.ac.gachon.oplanner.domain.LecTime;
import kr.ac.gachon.oplanner.domain.Lecture;

import java.util.Set;

public interface LecTimeRepository {
    LecTime save(LecTime lecTime);

    Set<LecTime> getTimes(Lecture lecture);
}
