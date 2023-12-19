package kr.ac.gachon.oplanner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import kr.ac.gachon.oplanner.domain.dbcolumns.LecTime;
import kr.ac.gachon.oplanner.domain.dbcolumns.Lecture;
import kr.ac.gachon.oplanner.domain.enums.DayEnum;
import kr.ac.gachon.oplanner.domain.enums.ClassificationEnum;
import kr.ac.gachon.oplanner.repository.interfaces.LecTimeRepository;
import kr.ac.gachon.oplanner.repository.interfaces.LectureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;

@Service
@Slf4j
public class LecAddService {
    private final LecTimeRepository lecTimeRepository;
    private final LectureRepository lectureRepository;

    public LecAddService(LecTimeRepository lecTimeRepository, LectureRepository lectureRepository) {
        this.lecTimeRepository = lecTimeRepository;
        this.lectureRepository = lectureRepository;
    }

    private static DayEnum parseDay(char day) {
        return switch (day) {
            case '월' -> DayEnum.MON;
            case '화' -> DayEnum.TUE;
            case '수' -> DayEnum.WED;
            case '목' -> DayEnum.THU;
            case '금' -> DayEnum.FRI;
            default -> null;
        };
    }

    private static Time parseStartTime(String time) {
        return switch (time) {
            case "A" -> Time.valueOf("09:30:00");
            case "B" -> Time.valueOf("11:00:00");
            case "C" -> Time.valueOf("13:00:00");
            case "D" -> Time.valueOf("14:30:00");
            case "E" -> Time.valueOf("16:00:00");
            default -> Time.valueOf(String.format("%02d:00:00", Integer.parseInt(time) + 8));
        };
    }

    private static Time parseEndTime(String time) {
        return switch (time) {
            case "A" -> Time.valueOf("10:45:00");
            case "B" -> Time.valueOf("12:15:00");
            case "C" -> Time.valueOf("14:15:00");
            case "D" -> Time.valueOf("15:45:00");
            case "E" -> Time.valueOf("17:15:00");
            default -> Time.valueOf(String.format("%02d:50:00", Integer.parseInt(time) + 8));
        };
    }

    private static ClassificationEnum parseClassification(String classificationString) {
        return switch (classificationString) {
            case "전필" -> ClassificationEnum.MAJOR_REQUIRED;
            case "전선" -> ClassificationEnum.MAJOR_ELECTIVE;
            default -> null;
        };
    }

    private static boolean validateLecName(Lecture lecture) {
        if (lecture.getLecName().contains("(외국인반)")) return false;

        if (lecture.getLecName().contains(" ")) {
            int i = lecture.getLecName().indexOf(" ");
            String substring = lecture.getLecName().substring(0, i);
            lecture.setLecName(substring);
        }
        return true;
    }

    @Transactional
    public boolean updateLectures(String lecInfoResponse) {
        lectureRepository.clear();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(lecInfoResponse);
            JsonNode lectures = jsonNode.get("rows");
            for (JsonNode lectureNode : lectures) {
                Lecture lecture = objectMapper.readValue(lectureNode.toString(), Lecture.class);
                if (!validateLecName(lecture)) continue;
                lecture.setClassification(parseClassification(lectureNode.get("isu_cd").asText()));
                log.info("[SaveLecture] {}", lecture);
                lectureRepository.save(lecture);
                saveLecTime(lectureNode.get("lec_time").asText(), lecture);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    /**
     * @param timeString "월1, 월2, 월3"
     * @param lecture
     */
    private void saveLecTime(String timeString, Lecture lecture) {
        String[] split = timeString.replaceAll(" ", "").split(",");
        // ["월1", "월2", "월3"]
        LecTime curLecTime = new LecTime();
        log.debug("[{}] SaveLecTime\tLecName = {}", lecture.getLecNum(), lecture.getLecName());
        for (int i = 0; i < split.length; i++) {
            log.debug("[{}] Time String = {}", lecture.getLecNum(), split[i]);
            DayEnum curDay = parseDay(split[i].charAt(0));
            String curTimeVal = split[i].substring(1);
            log.debug("[{}] curLecTime = \tcurDay = {}\tcurTimeVal={}", lecture.getLecNum(), curDay, curTimeVal);

            if (curLecTime.getDay() == null) {
                curLecTime.setDay(curDay);
                curLecTime.setStartTime(parseStartTime(curTimeVal));
            }

            if (i == split.length - 1 || curDay != parseDay(split[i + 1].charAt(0))) {
                curLecTime.setEndTime(parseEndTime(curTimeVal));
                curLecTime.setLecture(lecture);
                lecTimeRepository.save(curLecTime);
                curLecTime = new LecTime();
            }
        }
    }
}