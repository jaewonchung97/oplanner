package kr.ac.gachon.oplanner;

import jakarta.transaction.Transactional;
import kr.ac.gachon.oplanner.domain.LecTime;
import kr.ac.gachon.oplanner.domain.Lecture;
import kr.ac.gachon.oplanner.domain.enums.DayEnum;
import kr.ac.gachon.oplanner.repository.LecTimeRepository;
import kr.ac.gachon.oplanner.repository.LectureRepository;
import kr.ac.gachon.oplanner.service.LectureService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
@Transactional
class OplannerApplicationTests {

    @Autowired
    LecTimeRepository lecTimeRepository;
    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    LectureService lectureService;

    @BeforeEach
    void beforeEach() {
        lectureService.updateLectures(2022, 1);
    }

    @Test
    void getParsedTime() {
        Lecture lec = lectureRepository.getLectureByLecNum("13969001");
        Assertions.assertEquals("정보보호개론", lec.getLecName());
        List<Map<String, Object>> parsedTime = lectureService.getParsedTime(lec);
        Assertions.assertEquals(DayEnum.MON, DayEnum.valueOf(parsedTime.get(0).get("day").toString()));
        Assertions.assertIterableEquals(List.of(11, 12), (List<Integer>) parsedTime.get(0).get("time"));
        Assertions.assertEquals(DayEnum.TUE, DayEnum.valueOf(parsedTime.get(1).get("day").toString()));
        Assertions.assertIterableEquals(List.of(11, 12), (List<Integer>) parsedTime.get(1).get("time"));
    }

    @Test
    void getAllLecWithTimes() {
        Map<Lecture, Set<LecTime>> allLecWithTimes = lectureService.getAllLecWithTimes();
        System.out.println("lectureRepository = " + lectureRepository.getAllLectures());
        Lecture cgg = lectureRepository.getLectureByLecNum("06823001");
        Set<LecTime> lecTimes = allLecWithTimes.get(cgg);
        for (LecTime lecTime : lecTimes) {
            System.out.println("lecTime.getDay() = " + lecTime.getDay());
            System.out.println("lecTime.getTime() = " + lecTime.getTime());
        }
    }
}
