package kr.ac.gachon.oplanner.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.gachon.oplanner.domain.dbcolumns.Student;
import kr.ac.gachon.oplanner.domain.forms.StudentForm;
import kr.ac.gachon.oplanner.service.SessionConst;
import kr.ac.gachon.oplanner.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/home")
public class LoginController {

    private final StudentService studentService;

    public LoginController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/login")
    public String loginPage(@SessionAttribute(value = SessionConst.LOGIN_STUDENT, required = false) Student student, Model model) {
        if (student != null) {
            return "redirect:/home";
        } else {
            model.addAttribute(new StudentForm());
            return "login";
        }
    }

    @PostMapping("/login.do")
    public String login(@ModelAttribute StudentForm studentForm, HttpServletRequest request) {
        if (studentForm != null) {
            Student loginStudent = studentService.getStudentByForm(studentForm);
            log.info("Found Student = {}", loginStudent);
            if (loginStudent != null) {
                log.info("[{}] Login", studentForm.getNum());
                request.getSession().setAttribute(SessionConst.LOGIN_STUDENT, loginStudent);
            }
        }
        return "redirect:/home/login";
    }
}
