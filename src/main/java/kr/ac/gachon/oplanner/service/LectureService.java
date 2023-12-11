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

    private static LecTime setLecTime(String s, LecTime lecTime) {
        switch (s.substring(1)) {
            case "1" -> lecTime.setTime(1);
            case "A" -> lecTime.setTime(2);
            case "2" -> lecTime.setTime(3);
            case "3", "B" -> lecTime.setTime(4);
            case "4" -> lecTime.setTime(5);
            case "5", "C" -> lecTime.setTime(7);
            case "6" -> lecTime.setTime(8);
            case "D" -> lecTime.setTime(9);
            case "7" -> lecTime.setTime(10);
            case "8", "E" -> lecTime.setTime(11);
            case "9" -> lecTime.setTime(12);
            case "10" -> lecTime.setTime(14);
            case "11" -> lecTime.setTime(15);
            case "12" -> lecTime.setTime(16);
            case "13" -> lecTime.setTime(17);
        }
        LecTime lecTime2 = lecTime.clone();

        switch (s.substring(1)) {
            case "A" -> lecTime2.setTime(3);
            case "B" -> lecTime2.setTime(5);
            case "C" -> lecTime2.setTime(8);
            case "D" -> lecTime2.setTime(10);
            case "E" -> lecTime2.setTime(12);

            case "1" -> lecTime2.setTime(2);
            case "4" -> lecTime2.setTime(6);
            case "6" -> lecTime2.setTime(9);
            case "9" -> lecTime2.setTime(13);
            default -> lecTime2 = null;
        }
        return lecTime2;
    }

    private static void setDay(String s, LecTime lecTime) {
        switch (s.charAt(0)) {
            case '월' -> lecTime.setDay(DayEnum.MON);
            case '화' -> lecTime.setDay(DayEnum.TUE);
            case '수' -> lecTime.setDay(DayEnum.WED);
            case '목' -> lecTime.setDay(DayEnum.THU);
            case '금' -> lecTime.setDay(DayEnum.FRI);
            default -> throw new EnumConstantNotPresentException(DayEnum.class, s);
        }
    }

    @Transactional
    public boolean updateLectures(int year, int semester) {
        lectureRepository.clear();

        String response = getLecResponse(year, semester);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(response);
            JsonNode rows = jsonNode.get("rows");
            for (JsonNode row : rows) {
                Lecture lecture = objectMapper.readValue(row.toString(), Lecture.class);

                if (lecture.getLecName().contains("(외국인반)")) {
                    continue;
                }
                switch (row.get("isu_cd").asText()) {
                    case "전필" -> lecture.setClassification(LecClassification.MAJOR_REQUIRED);
                    case "전선" -> lecture.setClassification(LecClassification.MAJOR_ELECTIVE);
                }

                lectureRepository.save(lecture);

                String timeString = row.get("lec_time").asText();
                String[] split = timeString.replaceAll(" ", "").split(",");

                for (String s : split) {
                    LecTime lecTime = new LecTime(lecture, null, null);
                    setDay(s, lecTime);
                    LecTime lecTime2 = setLecTime(s, lecTime);
                    lecTimeRepository.save(lecTime);
                    if (lecTime2 != null) lecTimeRepository.save(lecTime2);
                }
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public Map<Lecture, Set<LecTime>> getAllLecWithTimes() {
        Map<Lecture, Set<LecTime>> result = new HashMap<>();
        List<Lecture> lectures = lectureRepository.getAllLectures();
        for (Lecture lecture : lectures) {
            Set<LecTime> times = lecTimeRepository.getTimes(lecture);
            result.put(lecture, times);
        }
        return result;
    }

    /**
     * @param lecture
     * @return [{day=TUE, time=[2, 3]}, {day=WED, time=[4, 5]}]
     */
    public List<Map<String, Object>> getParsedTime(Lecture lecture) {
        Set<LecTime> times = lecTimeRepository.getTimes(lecture);
        List<Map<String, Object>> result = new ArrayList<>();
        for (LecTime lecTime : times) {
            String day = lecTime.getDay().toString();
            if (result.isEmpty()) {
                result.add(Map.of("day", day, "time", new ArrayList<Integer>()));
            }

            Map<String, Object> dayUnit = null;
            for (Map<String, Object> unit : result) {
                if (unit.get("day").equals(day)){
                    dayUnit = unit;
                }
            }
            if (dayUnit == null){
                dayUnit = Map.of("day", day, "time", new ArrayList<Integer>());
                result.add(dayUnit);
            }

            List<Integer> timeList = (List<Integer>) dayUnit.get("time");
            timeList.add(lecTime.getTime());
            Collections.sort(timeList);
        }

        result.sort(Comparator.comparingInt(
                dayUnit -> DayEnum.valueOf(dayUnit.get("day").toString()).ordinal()
        ));

        return result;
    }

    // TODO
    public List<Lecture> getPossibleLectures(Student student) {
        attendanceRepository.getAttendedLecNames(student);
        return null;
    }

}