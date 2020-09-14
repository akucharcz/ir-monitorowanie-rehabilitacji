package com.ibm.server;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping("/")
    public String main() {

        return "main";
    }
    @GetMapping("/login_register.html")
    public String login() {

        return "login";
    }
    @GetMapping("/patients.html")
    public String patients() {

        return "patients";
    }
    @GetMapping("/single_patient.html")
    public String single_patient() {

        return "single_patient";
    }
    @GetMapping("/exercises.html")
    public String exercises() {

        return "exercises";
    }
}
