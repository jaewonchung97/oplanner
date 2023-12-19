package kr.ac.gachon.oplanner.repository.interfaces;

import kr.ac.gachon.oplanner.domain.dbcolumns.Attendance;
import kr.ac.gachon.oplanner.domain.dbcolumns.Student;

import java.util.List;

public interface AttendanceRepository {
    Attendance save(Attendance attendance);
    List<String> getAttendedLecNames(Student student);
}