package kr.ac.gachon.oplanner.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import kr.ac.gachon.oplanner.domain.Student;

import java.util.List;

public class JpaAttendanceRepository implements AttendanceRepository{
    private final EntityManager em;

    public JpaAttendanceRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<String> getAttendedLecNames(Student student) {
        Query query = em.createQuery("select lecName from Attendance where student=:student");
        query.setParameter("student", student);
        return query.getResultList();
    }
}
