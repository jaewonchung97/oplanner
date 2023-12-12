package kr.ac.gachon.oplanner.service;

import kr.ac.gachon.oplanner.domain.Student;
import kr.ac.gachon.oplanner.domain.LecTime;
import kr.ac.gachon.oplanner.domain.Lecture;
import kr.ac.gachon.oplanner.repository.AttendanceRepository;
import kr.ac.gachon.oplanner.repository.LecTimeRepository;
import kr.ac.gachon.oplanner.repository.LectureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class LectureService {
    private final LectureRepository lectureRepository;
    private final LecTimeRepository lecTimeRepository;

    private final AttendanceRepository attendanceRepository;

    public LectureService(LectureRepository lectureRepository, LecTimeRepository lecTimeRepository, AttendanceRepository attendanceRepository) {
        this.lectureRepository = lectureRepository;
        this.lecTimeRepository = lecTimeRepository;
        this.attendanceRepository = attendanceRepository;
    }


    public List<LecTime> getLecTimes(Lecture lecture) {
        return lecTimeRepository.getTimes(lecture);
    }

    public Lecture getLecByLecNum(String lecNum){
        return lectureRepository.getLectureByLecNum(lecNum);
    }

    public List<String> getAllLecNames(){
        return lectureRepository.getAllLecNames();
    }

    public List<Lecture> getLecturesByName(String lecName){
        return lectureRepository.getLecturesByName(lecName);
    }

    public List<Lecture> getAllLectures(){
        return lectureRepository.getAllLectures();
    }

    // TODO
    public List<Lecture> getPossibleLectures(Student student) {
        attendanceRepository.getAttendedLecNames(student);
        return null;
    }

}