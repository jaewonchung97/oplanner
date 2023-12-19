package kr.ac.gachon.oplanner.repository.jpaimplements;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import kr.ac.gachon.oplanner.domain.dbcolumns.Lecture;
import kr.ac.gachon.oplanner.domain.dbcolumns.Student;
import kr.ac.gachon.oplanner.repository.interfaces.LectureRepository;

import java.util.List;

@Transactional
public class JpaLectureRepository implements LectureRepository {

    private final EntityManager em;

    public JpaLectureRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Lecture save(Lecture lecture) {
        em.persist(lecture);
        return lecture;
    }

    @Override
    public Lecture getLectureByLecNum(String lecNum) {
        return em.find(Lecture.class, lecNum);
    }

    @Override
    public List<Lecture> getAllLectures() {
        TypedQuery<Lecture> query = em.createQuery("select l from Lecture l", Lecture.class);
        return query.getResultList();
    }

    @Override
    public List<String> getAllLecNames() {
        TypedQuery<String> query = em.createQuery("select distinct l.lecName from Lecture l", String.class);
        return query.getResultList();
    }

    @Override
    public List<Lecture> getLecturesByName(String lecName) {
        TypedQuery<Lecture> query = em.createQuery("select l from Lecture l where l.lecName=:lecName", Lecture.class);
        query.setParameter("lecName", lecName);

        return query.getResultList();
    }

    @Override
    public List<Object[]> getPosLecInfos(Student student) {
        String jpqlQuery = "SELECT DISTINCT l.lecName, l.classification, l.credit " +
                "FROM Lecture l " +
                "WHERE l.lecName NOT IN " +
                "(SELECT a.lecName FROM Attendance a WHERE a.student = :student)" +
                "ORDER BY l.classification, l.lecName";
        TypedQuery<Object[]> query = em.createQuery(jpqlQuery, Object[].class);
        query.setParameter("student", student);
        return query.getResultList();

    }

    @Override
    public void clear() {
        em.createQuery("delete from Lecture").executeUpdate();
    }
}
