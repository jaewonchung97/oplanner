package kr.ac.gachon.oplanner.repository;

import kr.ac.gachon.oplanner.domain.Lecture;

import java.util.List;

public interface LectureRepository {
    Lecture save(Lecture lecture);

    Lecture getLectureByLecNum(String lecNum);

    List<Lecture> getAllLectures();

    void clear();
}
