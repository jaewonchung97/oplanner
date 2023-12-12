package kr.ac.gachon.oplanner;

import jakarta.transaction.Transactional;
import kr.ac.gachon.oplanner.domain.LecTime;
import kr.ac.gachon.oplanner.domain.Lecture;
import kr.ac.gachon.oplanner.domain.enums.DayEnum;
import kr.ac.gachon.oplanner.repository.LecTimeRepository;
import kr.ac.gachon.oplanner.repository.LectureRepository;
import kr.ac.gachon.oplanner.service.LecAddService;
import kr.ac.gachon.oplanner.service.LectureService;

import kr.ac.gachon.oplanner.utils.WebRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
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
    @Autowired
    LecAddService lecAddService;

    @BeforeEach
    void beforeEach() {
        lecAddService.updateLectures(WebRequest.getLecResponse(2022, 1));
    }

    @Test
    void getAllLecWithTimesTest() {
        Lecture cgg = lectureRepository.getLectureByLecNum("06823001");
        //화A ,수B
        Assertions.assertThat(cgg.getLecName()).isEqualTo("컴퓨터공학개론");
        List<LecTime> cggLecTimes = lectureService.getLecTimes(cgg);


        LecTime tue = new LecTime(cgg, Time.valueOf("09:30:00"), Time.valueOf("10:45:00"), DayEnum.TUE);
        LecTime wed = new LecTime(cgg, Time.valueOf("11:00:00"), Time.valueOf("12:15:00"), DayEnum.WED);

        Assertions.assertThat(cggLecTimes).contains(tue).contains(wed);


        Lecture op = lectureRepository.getLectureByLecNum("12044002");
        List<LecTime> opTimes = lectureService.getLecTimes(op);

        LecTime op_wed = new LecTime(op, Time.valueOf("11:00:00"), Time.valueOf("12:50:00"), DayEnum.WED);
        LecTime op_thr = new LecTime(op, Time.valueOf("09:00:00"), Time.valueOf("10:50:00"), DayEnum.THU);

        Assertions.assertThat(opTimes).contains(op_wed).contains(op_thr);
    }
}
