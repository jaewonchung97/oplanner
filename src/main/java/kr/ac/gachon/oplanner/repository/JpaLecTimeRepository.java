package kr.ac.gachon.oplanner.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import kr.ac.gachon.oplanner.domain.LecTime;
import kr.ac.gachon.oplanner.domain.Lecture;

import java.util.List;

@Transactional
public class JpaLecTimeRepository implements LecTimeRepository {

    private final EntityManager em;

    public JpaLecTimeRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public LecTime save(LecTime lecTime) {
        em.persist(lecTime);
        return lecTime;
    }

    @Override
    public List<LecTime> getTimes(Lecture lecture) {
        TypedQuery<LecTime> query = em.createQuery("select l from LecTime l where l.lecture=:lecture", LecTime.class);
        query.setParameter("lecture", lecture);
        return query.getResultList();
    }
}
