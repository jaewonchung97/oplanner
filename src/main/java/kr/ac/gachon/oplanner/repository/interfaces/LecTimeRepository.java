package kr.ac.gachon.oplanner.repository.interfaces;

import kr.ac.gachon.oplanner.domain.dbcolumns.LecTime;
import kr.ac.gachon.oplanner.domain.dbcolumns.Lecture;

import java.util.List;

public interface LecTimeRepository {
    LecTime save(LecTime lecTime);

    List<LecTime> getTimes(Lecture lecture);

    List<LecTime> getAllLecTimes(List<Lecture> timeTable);
}
