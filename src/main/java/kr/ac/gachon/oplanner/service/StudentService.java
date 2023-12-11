package kr.ac.gachon.oplanner.service;

import kr.ac.gachon.oplanner.domain.Student;
import kr.ac.gachon.oplanner.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getOrSaveStudent(Student student) {
        return studentRepository.findByStNum(student.getStNum()).orElseGet(() -> studentRepository.save(student));
    }
}
