//package kr.ac.gachon.oplanner.utils.web;
//
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.io.File;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Controller
//@Slf4j
//public class GetLoginInfo {
//    private static void setOptions(FirefoxOptions options) {
//        options.addArguments("--headless");
//        options.addArguments("--disable-gpu");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-popup-blocking");
//        options.addArguments("--disable-default-apps");
//    }
//
//    @PostConstruct
//    public void init() {
//        File file = new File("geckodriver");
//        log.info("Geckodriver Executable = {}", file.canExecute());
//        log.info("Geckodriver path = {}", file.getAbsolutePath());
//        System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
//
//    }
//
//    @GetMapping("/test/selenium")
//    @ResponseBody
//    public String seleniumTest() {
//        log.info("Selenium Start");
//        FirefoxOptions options = new FirefoxOptions();
//        setOptions(options);
//        WebDriver driver = new FirefoxDriver(options);
//        driver.get("www.naver.com");
//        return driver.getPageSource();
//    }
//}
