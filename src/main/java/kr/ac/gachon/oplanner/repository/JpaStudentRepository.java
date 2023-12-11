package kr.ac.gachon.oplanner.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.ac.gachon.oplanner.domain.Student;

import java.util.Optional;

@Transactional
public class JpaStudentRepository implements StudentRepository {

    private final EntityManager em;

    public JpaStudentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Student save(Student student) {
        em.persist(student);
        return student;
    }

    @Override
    public Optional<Student> findByStNum(int stNum) {
        Student student = em.find(Student.class, stNum);
        return Optional.ofNullable(student);
    }
}
