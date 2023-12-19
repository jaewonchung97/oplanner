package kr.ac.gachon.oplanner.repository.interfaces;

import kr.ac.gachon.oplanner.domain.dbcolumns.Student;
import kr.ac.gachon.oplanner.domain.forms.StudentForm;

import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);

    Optional<Student> validateStudentExist(StudentForm studentForm);

    Optional<Student> findByStNum(int stNum);
}
