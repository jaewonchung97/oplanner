package kr.ac.gachon.oplanner.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.ac.gachon.oplanner.domain.Student;
import kr.ac.gachon.oplanner.service.StudentService;
import kr.ac.gachon.oplanner.service.login.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/test")
public class TestController {
    private final StudentService studentService;

    public TestController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/login")
    public String loginPage(Model model, @SessionAttribute(name = SessionConst.LOGIN_STUDENT, required = false) Student student) {
        if (student == null) {
            model.addAttribute(new Student());
            return "login";
        }

        return "redirect:/";
    }
    @ResponseBody
    @GetMapping("")
    public Map<String, String> getHome(@SessionAttribute(name = SessionConst.LOGIN_STUDENT, required = false) Student student) {
        if (student == null) return Map.of("Status", "200", "Message", "Hello");
        return Map.of("Status", "200", "UserName", student.getStName(), "StNum", Integer.toString(student.getStNum()));
    }
}
