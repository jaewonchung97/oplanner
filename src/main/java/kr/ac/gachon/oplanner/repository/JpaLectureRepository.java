package kr.ac.gachon.oplanner.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import kr.ac.gachon.oplanner.domain.Lecture;

import java.util.List;

@Transactional
public class JpaLectureRepository implements LectureRepository{

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
    public void clear() {
        em.createQuery("delete from Lecture").executeUpdate();
    }
}
