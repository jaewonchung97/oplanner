package kr.ac.gachon.oplanner.repository;

import kr.ac.gachon.oplanner.domain.Student;

import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);

    Optional<Student> findByStNum(int stNum);
}
