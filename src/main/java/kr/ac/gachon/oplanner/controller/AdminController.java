package kr.ac.gachon.oplanner.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.gachon.oplanner.domain.Lecture;
import kr.ac.gachon.oplanner.domain.Student;
import kr.ac.gachon.oplanner.service.LecAddService;
import kr.ac.gachon.oplanner.service.LectureService;
import kr.ac.gachon.oplanner.service.StudentService;
import kr.ac.gachon.oplanner.service.login.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StudentService studentService;
    private final LectureService lectureService;

    private final LecAddService lecAddService;

    public AdminController(StudentService studentService, LectureService lectureService, LecAddService lecAddService) {
        this.studentService = studentService;
        this.lectureService = lectureService;
        this.lecAddService = lecAddService;
    }

    @GetMapping("login")
    public String loginPage(Model model, @SessionAttribute(name = SessionConst.LOGIN_STUDENT, required = false) Student student) {
        if (student == null) {
            model.addAttribute(new Student());
            return "login";
        }
        return "redirect:/";
    }

    @PostMapping("login.do")
    public String login(@ModelAttribute Student student, HttpServletRequest request){
        if(student != null){
//            Student studentFromDB = studentService.getOrSaveStudent(student);
            request.getSession().setAttribute(SessionConst.LOGIN_STUDENT, student);
        }
        return "redirect:/admin";
    }
    @GetMapping("")
    public String defaultPage(@SessionAttribute(name = SessionConst.LOGIN_STUDENT, required = false)Student student){
        if (student == null) return "redirect:/admin/login";
        else if (student.getStNum() != 202031233) {
            return "redirect:/admin/login";
        }
        return "admin";
    }

    @PostMapping("add.do")
    @ResponseBody
    public Map<String, String> addLectures(@RequestBody String lecInfoResponse){
        boolean saveLectures = lecAddService.updateLectures(lecInfoResponse);
        log.info("SaveLecture = {}", saveLectures);
        if (saveLectures){
            return Map.of("Status", "200");
        }
        return Map.of("Stauts", "500", "Message", "Failed");
    }
}
