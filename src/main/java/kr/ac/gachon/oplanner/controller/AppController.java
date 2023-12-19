package kr.ac.gachon.oplanner.controller;

import kr.ac.gachon.oplanner.domain.dbcolumns.Student;
import kr.ac.gachon.oplanner.service.LectureService;
import kr.ac.gachon.oplanner.service.RecommendService;
import kr.ac.gachon.oplanner.service.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home")
@Slf4j
public class AppController {
    private final LectureService lectureService;
    private final RecommendService recommendService;

    public AppController(LectureService lectureService, RecommendService recommendService) {
        this.lectureService = lectureService;
        this.recommendService = recommendService;
    }

    @GetMapping("")
    public String homePage(@SessionAttribute(value = SessionConst.LOGIN_STUDENT, required = false) Student student, Model model) {
        if (student == null) return "redirect:/home/login";
        model.addAttribute("lectures", lectureService.getPossibleLectures(student));
        return "select_lec";
    }

    @PostMapping("recommend.do")
    public String recommend(
            @SessionAttribute(value = SessionConst.LOGIN_STUDENT) Student student,
            @RequestParam("selectedLec") List<String> selectedNames,
            Model model){
//        List<List<Lecture>> posTimeTables = recommendService.getPosTimeTables(selectedNames);
//        model.addAttribute("timeTables", posTimeTables);
        return "show_lec";
    }
}
