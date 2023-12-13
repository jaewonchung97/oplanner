package kr.ac.gachon.oplanner.service;
import kr.ac.gachon.oplanner.domain.enums.DayEnum;
import kr.ac.gachon.oplanner.domain.LecTime;
import kr.ac.gachon.oplanner.domain.Lecture;
import kr.ac.gachon.oplanner.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimetableService {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LecTimeService lecTimeService;

    public String getAlgorithmDescription() {
        return "최대한 월금 수업을 제외함";
    }

    public List<Lecture> generateTimetable(Student student) {
        List<Lecture> possibleLectures = lectureService.getPossibleLectures(student);

        // 최대한 월, 금 수업을 빼고 학점이 15점이 되도록 하는 알고리즘 추가
        List<Lecture> timetable = new ArrayList<>();
        int totalCredits = 0;

        for (Lecture lecture : possibleLectures) {
            if (totalCredits + lecture.getCredit() <= 15) {
                boolean isMondayFriday = true;
                List<LecTime> lecTimes = lecTimeService.getLecTimes(lecture);

                for (LecTime lecTime : lecTimes) {
                    if (lecTime.getDay() == DayEnum.MON || lecTime.getDay() == DayEnum.FRI) {
                        isMondayFriday = false;
                        break;
                    }
                }

                if (isMondayFriday) {
                    timetable.add(lecture);
                    totalCredits += lecture.getCredit();
                }
            }
        }

        return timetable;
    }
}
