package kr.ac.gachon.oplanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactRouterController {
    @GetMapping(value = {"", "/main"})
    public String forwardIndex(){
        return "forward:/index.html";
    }
}
