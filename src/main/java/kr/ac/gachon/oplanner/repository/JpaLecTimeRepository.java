package kr.ac.gachon.oplanner.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import kr.ac.gachon.oplanner.domain.LecTime;
import kr.ac.gachon.oplanner.domain.Lecture;

import java.util.HashSet;
import java.util.Set;

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
    public Set<LecTime> getTimes(Lecture lecture) {
        Query query = em.createQuery("select l from LecTime l where lecture=:lecture");
        query.setParameter("lecture", lecture);
        return new HashSet<LecTime>(query.getResultList());
    }
}
