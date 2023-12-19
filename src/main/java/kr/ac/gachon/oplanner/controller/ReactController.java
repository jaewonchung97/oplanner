package kr.ac.gachon.oplanner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.gachon.oplanner.domain.dbcolumns.Lecture;
import kr.ac.gachon.oplanner.domain.dbcolumns.Student;
import kr.ac.gachon.oplanner.domain.forms.ConstrainForm;
import kr.ac.gachon.oplanner.domain.forms.LectureInfo;
import kr.ac.gachon.oplanner.domain.forms.StudentForm;
import kr.ac.gachon.oplanner.domain.forms.TimeTableForm;
import kr.ac.gachon.oplanner.service.LectureService;
import kr.ac.gachon.oplanner.service.RecommendService;
import kr.ac.gachon.oplanner.service.SessionConst;
import kr.ac.gachon.oplanner.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@Slf4j
public class ReactController {
    private final StudentService studentService;
    private final LectureService lectureService;
    private final RecommendService recommendService;
    private final ObjectMapper mapper = new ObjectMapper();

    public ReactController(StudentService studentService, LectureService lectureService, RecommendService recommendService) {
        this.studentService = studentService;
        this.lectureService = lectureService;
        this.recommendService = recommendService;
    }

    @GetMapping("/lecture_infos")
    public List<Map<String, String>> getPosLecNames(@SessionAttribute(SessionConst.LOGIN_STUDENT) Student student) {
        List<LectureInfo> possibleLectures = lectureService.getPossibleLectures(student);
        List<Map<String, String>> response = new ArrayList<>();
        for (LectureInfo lecture : possibleLectures) {
            Map<String, String> lectureInfo = new HashMap<>();
            lectureInfo.put("name", lecture.getLecName());
            lectureInfo.put("type", lecture.getClassification());
            lectureInfo.put("credit", String.valueOf(lecture.getCredit()));
            response.add(lectureInfo);
        }
        return response;
    }

    @GetMapping("/user_info")
    public String getUserInfo(@SessionAttribute(SessionConst.LOGIN_STUDENT) Student student){
        try {
            return mapper.writeValueAsString(student);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login.do")
    public String login(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> readValue;
        try {
            readValue = mapper.readValue(requestBody, new TypeReference<Map<String, String>>() {});
            log.info("readValue = {}", readValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        StudentForm studentForm = new StudentForm(readValue.get("username"), Integer.parseInt(readValue.get("usernumber")));
        Student loginStudent = studentService.getStudentByForm(studentForm);
        log.info("Found Student = {}", loginStudent);

        if (loginStudent != null) {
            log.info("[{}] Login", loginStudent.getStNum());
            request.getSession().setAttribute(SessionConst.LOGIN_STUDENT, loginStudent);
            return "login succeeded";
        } else {
            response.setStatus(400);
            return "login failed";
        }
    }

    @PostMapping("/recommend")
    public String recommendLec(
            @SessionAttribute(SessionConst.LOGIN_STUDENT) Student student,
            @RequestBody String requestBody

    ){
        log.info("requestBody = {}", requestBody);
        Map<String, Object> request;
        try {
            request = mapper.readValue(requestBody, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ConstrainForm constrainForm = new ConstrainForm((List<String>) request.get("desiredDaysOff"), (int) request.get("desiredCredits"), (List<String>) request.get("desiredLecture"));
        log.info("constrainForm = {}", constrainForm);

        List<List<Lecture>> allPosTimeTables = recommendService.getAllPosTimeTables(constrainForm);
        List<List<TimeTableForm>> result = new ArrayList<>();
        for (List<Lecture> timeTable : allPosTimeTables) {
            List<TimeTableForm> tableTimes = lectureService.getTableTimes(timeTable);
            result.add(tableTimes);
        }
        try {
            return mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
