package kr.ac.gachon.oplanner.repository.jpaimplements;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.ac.gachon.oplanner.domain.dbcolumns.Student;
import kr.ac.gachon.oplanner.domain.forms.StudentForm;
import kr.ac.gachon.oplanner.repository.interfaces.StudentRepository;

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
    public Optional<Student> validateStudentExist(StudentForm studentForm) {
        Student foundSt = em.find(Student.class, studentForm.getNum());
        if (foundSt == null || !foundSt.getStName().equals(studentForm.getName())) return Optional.empty();
        return Optional.of(foundSt);
    }

    @Override
    public Optional<Student> findByStNum(int stNum) {
        Student student = em.find(Student.class, stNum);
        return Optional.ofNullable(student);
    }
}
