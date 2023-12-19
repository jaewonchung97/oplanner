package kr.ac.gachon.oplanner.service;

import kr.ac.gachon.oplanner.domain.dbcolumns.Student;
import kr.ac.gachon.oplanner.domain.forms.StudentForm;
import kr.ac.gachon.oplanner.repository.interfaces.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudentByForm(StudentForm studentForm) {
        Student student = studentRepository.validateStudentExist(studentForm).orElse(null);
        if (student != null) {
            if (student.getStName().equals(studentForm.getName())) return student;
        }

        return null;
    }
}
