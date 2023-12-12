package kr.ac.gachon.oplanner.controller;

import kr.ac.gachon.oplanner.domain.LecTime;
import kr.ac.gachon.oplanner.domain.Lecture;
import kr.ac.gachon.oplanner.service.LectureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/test")
@ResponseBody
public class TestController {
    private final LectureService lectureService;

    public TestController(LectureService lectureService) {
        this.lectureService = lectureService;
    }


    @GetMapping("/lecture")
    public List<Lecture> getAllLectures() {
        return lectureService.getAllLectures();
    }


    @GetMapping("/lecture/name")
    public List<String> getAllLecNames() {
        return lectureService.getAllLecNames();
    }

    @GetMapping("/lecture/name/{lecName}")
    public List<Lecture> getLecturesByName(@PathVariable("lecName") String lecName) {
        return lectureService.getLecturesByName(lecName);
    }

    @GetMapping("/lecture/{lecNum}")
    public Lecture getLecByNum(@PathVariable("lecNum") String lecNum) {
        return lectureService.getLecByLecNum(lecNum);
    }

    @GetMapping("/lecture/{lecNum}/time")
    public List<LecTime> getLecTime(@PathVariable("lecNum") String lecNum) {
        return lectureService.getLecTimes(lectureService.getLecByLecNum(lecNum));
    }

}
