package kr.ac.gachon.oplanner.service;

import kr.ac.gachon.oplanner.domain.forms.LectureInfo;
import kr.ac.gachon.oplanner.domain.dbcolumns.Student;
import kr.ac.gachon.oplanner.domain.dbcolumns.LecTime;
import kr.ac.gachon.oplanner.domain.dbcolumns.Lecture;
import kr.ac.gachon.oplanner.domain.enums.ClassificationEnum;
import kr.ac.gachon.oplanner.domain.forms.TimeTableForm;
import kr.ac.gachon.oplanner.repository.interfaces.AttendanceRepository;
import kr.ac.gachon.oplanner.repository.interfaces.LecTimeRepository;
import kr.ac.gachon.oplanner.repository.interfaces.LectureRepository;
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

    public List<TimeTableForm> getTableTimes(List<Lecture> timeTable) {
        List<TimeTableForm> result = new ArrayList<>();
        List<LecTime> allLecTimes = lecTimeRepository.getAllLecTimes(timeTable);
        allLecTimes.forEach(lecTime -> result.add(new TimeTableForm(lecTime)));
        return result;
    }


    public Lecture getLecByLecNum(String lecNum) {
        return lectureRepository.getLectureByLecNum(lecNum);
    }

    public List<String> getAllLecNames() {
        return lectureRepository.getAllLecNames();
    }

    public List<Lecture> getLecturesByName(String lecName) {
        return lectureRepository.getLecturesByName(lecName);
    }

    public List<Lecture> getAllLectures() {
        return lectureRepository.getAllLectures();
    }

    public List<LectureInfo> getPossibleLectures(Student student) {
        List<Object[]> lecInfos = lectureRepository.getPosLecInfos(student);
        List<LectureInfo> result = new ArrayList<>();
        for (Object[] lecInfo : lecInfos) {
            result.add(new LectureInfo((String) lecInfo[0], (ClassificationEnum) lecInfo[1], (int) lecInfo[2]));
        }
        return result;
    }

    public void printTimeTable(List<Lecture> lectures) {
        List<LecTime> times = lecTimeRepository.getAllLecTimes(lectures);
        log.info("TimeTable ---------------------");
        for (LecTime time : times) {
            log.info("[{}] {} ~ {}\t{}({})", time.getDay(), time.getStartTime(), time.getEndTime(), time.getLecture().getLecName(), time.getLecture().getLecNum());
        }

    }


}