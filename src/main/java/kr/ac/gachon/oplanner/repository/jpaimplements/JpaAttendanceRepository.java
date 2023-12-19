package kr.ac.gachon.oplanner.repository.jpaimplements;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import kr.ac.gachon.oplanner.domain.dbcolumns.Attendance;
import kr.ac.gachon.oplanner.domain.dbcolumns.Student;
import kr.ac.gachon.oplanner.repository.interfaces.AttendanceRepository;

import java.util.List;

public class JpaAttendanceRepository implements AttendanceRepository {
    private final EntityManager em;

    public JpaAttendanceRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Attendance save(Attendance attendance) {
        em.persist(attendance);
        return attendance;
    }

    @Override
    public List<String> getAttendedLecNames(Student student) {
        Query query = em.createQuery("select lecName from Attendance where student=:student");
        query.setParameter("student", student);
        return query.getResultList();
    }
}
