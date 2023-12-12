package kr.ac.gachon.oplanner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import kr.ac.gachon.oplanner.domain.Student;
import kr.ac.gachon.oplanner.domain.enums.DayEnum;
import kr.ac.gachon.oplanner.domain.enums.LecClassification;
import kr.ac.gachon.oplanner.domain.LecTime;
import kr.ac.gachon.oplanner.domain.Lecture;
import kr.ac.gachon.oplanner.repository.AttendanceRepository;
import kr.ac.gachon.oplanner.repository.LecTimeRepository;
import kr.ac.gachon.oplanner.repository.LectureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;

import static kr.ac.gachon.oplanner.utils.WebRequest.getLecResponse;

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

    public Lecture getLectureByLecNum(String lecNum){
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