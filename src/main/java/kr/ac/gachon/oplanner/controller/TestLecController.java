package kr.ac.gachon.oplanner.controller;

import kr.ac.gachon.oplanner.domain.dbcolumns.LecTime;
import kr.ac.gachon.oplanner.domain.dbcolumns.Lecture;
import kr.ac.gachon.oplanner.service.LecAddService;
import kr.ac.gachon.oplanner.service.LectureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/test/lecture")
@ResponseBody
public class TestLecController {
    private final LectureService lectureService;
    private final LecAddService lecAddService;

    public TestLecController(LectureService lectureService, LecAddService lecAddService) {
        this.lectureService = lectureService;
        this.lecAddService = lecAddService;
    }

    @GetMapping("")
    public List<Lecture> getAllLectures() {
        return lectureService.getAllLectures();
    }

    @GetMapping("name")
    public List<String> getAllLecNames() {
        return lectureService.getAllLecNames();
    }

    @GetMapping("name/{lecName}")
    public List<Lecture> getLecturesByName(@PathVariable("lecName") String lecName) {
        return lectureService.getLecturesByName(lecName);
    }

    @GetMapping("lecture/{lecNum}")
    public Lecture getLecByNum(@PathVariable("lecNum") String lecNum) {
        return lectureService.getLecByLecNum(lecNum);
    }

    @GetMapping("{lecNum}/time")
    public List<LecTime> getLecTime(@PathVariable("lecNum") String lecNum) {
        return lectureService.getLecTimes(lectureService.getLecByLecNum(lecNum));
    }

    @PostMapping("/post.do")
    public Map<String, String> getPostData(@RequestBody String data) {
        log.info("[Post Request] = {}", data);
        return Map.of("status", "200", "message", "Hello");
    }

    @PostMapping("/add.do")
    public Map<String, String> addData(@RequestBody String lectures){
        lecAddService.updateLectures(lectures);
        return Map.of("status", "200", "message", "Succeed");
    }

}
