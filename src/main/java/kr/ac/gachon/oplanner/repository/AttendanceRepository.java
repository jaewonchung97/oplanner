package kr.ac.gachon.oplanner.repository;

import kr.ac.gachon.oplanner.domain.Student;

import java.util.List;

public interface AttendanceRepository {
    List<String> getAttendedLecNames(Student student);
}