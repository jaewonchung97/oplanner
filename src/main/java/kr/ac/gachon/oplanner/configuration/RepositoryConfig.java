package kr.ac.gachon.oplanner.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.ac.gachon.oplanner.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @PersistenceContext
    private EntityManager em;

    @Bean
    public StudentRepository studentRepository() {
        return new JpaStudentRepository(em);
    }

    @Bean
    public LecTimeRepository lecTimeRepository() {
        return new JpaLecTimeRepository(em);
    }

    @Bean
    public LectureRepository lectureRepository() {
        return new JpaLectureRepository(em);
    }

    @Bean
    public AttendanceRepository attendanceRepository() {
        return new JpaAttendanceRepository(em);
    }
}
