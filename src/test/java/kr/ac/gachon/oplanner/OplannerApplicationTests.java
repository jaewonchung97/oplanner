package kr.ac.gachon.oplanner;

import jakarta.transaction.Transactional;
import kr.ac.gachon.oplanner.domain.dbcolumns.LecTime;
import kr.ac.gachon.oplanner.domain.dbcolumns.Lecture;
import kr.ac.gachon.oplanner.domain.forms.ConstrainForm;
import kr.ac.gachon.oplanner.domain.enums.DayEnum;
import kr.ac.gachon.oplanner.repository.interfaces.LecTimeRepository;
import kr.ac.gachon.oplanner.repository.interfaces.LectureRepository;
import kr.ac.gachon.oplanner.service.LecAddService;
import kr.ac.gachon.oplanner.service.LectureService;
import kr.ac.gachon.oplanner.service.RecommendService;
import kr.ac.gachon.oplanner.service.StudentService;
import kr.ac.gachon.oplanner.utils.WebRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.util.List;

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
    RecommendService recommendService;

    @Autowired
    StudentService studentService;

    @BeforeAll
    static void init(@Autowired LecAddService lecAddService) {
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

    @Test
    void timeOverlapped() {
        // 화 5678
        String lecNum_1 = "09038006";
        // 월D, 화D
        String lecNum_2 = "14205004";

        Lecture lec1 = lectureRepository.getLectureByLecNum(lecNum_1);
        Lecture lec2 = lectureRepository.getLectureByLecNum(lecNum_2);

//        Assertions.assertThat(lecTimeRepository.isTimeOverlapped(List.of(lec1, lec2))).isTrue();


        // 화 5678
        lecNum_1 = "09038006";
        // 화 1234
        lecNum_2 = "09038005";

        lec1 = lectureRepository.getLectureByLecNum(lecNum_1);
        lec2 = lectureRepository.getLectureByLecNum(lecNum_2);

//        Assertions.assertThat(lecTimeRepository.isTimeOverlapped(List.of(lec1, lec2))).isFalse();
    }

    @Test
    void recommend() {
        List<String> lectures = List.of("리눅스", "모바일프로그래밍", "알고리즘", "웹프로그래밍", "IT특강", "데이터분석처리");
        ConstrainForm constrainForm = new ConstrainForm(List.of("MON", "FRI"), 15, lectures);
        List<List<Lecture>> posTimeTables = recommendService.getAllPosTimeTables(constrainForm);
        for (List<Lecture> posTimeTable : posTimeTables) {
            lectureService.printTimeTable(posTimeTable);
        }
//        StudentForm studentForm = new StudentForm("정재원", 202031233);
//        Student student = studentService.getStudentByForm(studentForm);
//        List<LectureInfo> possibleLectures = lectureService.getPossibleLectures(student);
//        System.out.println("possibleLecNames = " + possibleLectures);
    }

    @Test
    void getAllLecTimes() {
        Time time = new Time(15, 0, 0);
        Time time1 = new Time(17, 0, 0);
        long diff = time1.getTime() - time.getTime();
        System.out.println("diff = " + diff);
        System.out.println("diff/60 = " + diff / 60);
        System.out.println("diff/60/60 = " + diff / 60 / 60);
        System.out.println("diff/60/60/60 = " + diff / 60 / 60 / 60);
        //7200000
    }
}
