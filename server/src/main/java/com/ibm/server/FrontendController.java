package com.ibm.server;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.LinkedHashMap;
import java.util.Map;



@Controller
public class FrontendController {

    @GetMapping("/login_register")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String main() {
        return "main";
    }


/*
    @GetMapping("/")
    public String main() {

        return "main";
    }


    @GetMapping("/login_register.html")
    public String login() {

        return "login";
    }

 */
    @GetMapping("/patients.html")
    public String patients() {

        return "patients";
    }
    @GetMapping("/single_patient.html")
    public String single_patient(Model model) {
    Map<String, Integer> surveyMap = new LinkedHashMap<>();
    surveyMap.put("Godzina",40 );
        surveyMap.put("Doba",20 );
        surveyMap.put("Tydzień",10 );
        surveyMap.put("Miesiąc", 5);
        model.addAttribute("surveyMap", surveyMap);
        return "single_patient";
    }
    @GetMapping("/exercises.html")
    public String exercises() {

        return "exercises";
    }
}
