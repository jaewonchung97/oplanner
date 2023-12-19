package kr.ac.gachon.oplanner.repository.interfaces;

import kr.ac.gachon.oplanner.domain.dbcolumns.Lecture;
import kr.ac.gachon.oplanner.domain.dbcolumns.Student;

import java.util.List;

public interface LectureRepository {
    Lecture save(Lecture lecture);

    Lecture getLectureByLecNum(String lecNum);

    List<Lecture> getAllLectures();

    List<String> getAllLecNames();

    List<Lecture> getLecturesByName(String lecName);

    List<Object[]> getPosLecInfos(Student student);

    void clear();
}
