package kr.ac.gachon.oplanner.repository.jpaimplements;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import kr.ac.gachon.oplanner.domain.dbcolumns.LecTime;
import kr.ac.gachon.oplanner.domain.dbcolumns.Lecture;
import kr.ac.gachon.oplanner.repository.interfaces.LecTimeRepository;

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

    @Override
    public List<LecTime> getAllLecTimes(List<Lecture> timeTable) {
        String jpaQuery = "select lt from LecTime lt where lt.lecture in :timeTable order by day, startTime, endTime";
        TypedQuery<LecTime> query = em.createQuery(jpaQuery, LecTime.class);
        query.setParameter("timeTable", timeTable);
        return query.getResultList();
    }

}
