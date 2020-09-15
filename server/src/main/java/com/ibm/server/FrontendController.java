package com.ibm.server;

import com.ibm.server.model.ChartStructure;
import com.ibm.server.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Controller
@RequiredArgsConstructor
public class FrontendController {

    private final ChartService chartService;

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
    public String single_patient(Model model) {
        AtomicInteger inte1 = new AtomicInteger(1);
        AtomicInteger inte2 = new AtomicInteger(1);
        AtomicInteger inte3 = new AtomicInteger(1);
        AtomicInteger inte4 = new AtomicInteger(1);



        var hoursData = chartService.getChartData(ChartStructure.builder()
                .login("TestUser")
                .period("1")
                .build());

        var daysData = chartService.getChartData(ChartStructure.builder()
                .login("TestUser")
                .period("2")
                .build());

        var weeksData = chartService.getChartData(ChartStructure.builder()
                .login("TestUser")
                .period("3")
                .build());

        var monthsData = chartService.getChartData(ChartStructure.builder()
                .login("TestUser")
                .period("4")
                .build());

    Map<String, Integer> surveyMapHours = new LinkedHashMap<>();
        Map<String, Integer> surveyMapDays = new LinkedHashMap<>();
        Map<String, Integer> surveyMapWeeks = new LinkedHashMap<>();
        Map<String, Integer> surveyMapMonths = new LinkedHashMap<>();

        hoursData.forEach(
                data -> surveyMapHours.put(String.valueOf(inte1.getAndIncrement()), data)
        );

        daysData.forEach(
                data -> surveyMapDays.put(String.valueOf(inte2.getAndIncrement()), data)
        );

        weeksData.forEach(
                data -> surveyMapWeeks.put(String.valueOf(inte3.getAndIncrement()), data)
        );

        monthsData.forEach(
                data -> surveyMapMonths.put(String.valueOf(inte4.getAndIncrement()), data)
        );


        model.addAttribute("surveyMapHours", surveyMapHours);
        model.addAttribute("surveyMapDays", surveyMapDays);
        model.addAttribute("surveyMapWeeks", surveyMapWeeks);
        model.addAttribute("surveyMapMonths", surveyMapMonths);
        return "single_patient";
    }
    @GetMapping("/exercises.html")
    public String exercises() {

        return "exercises";
    }
}
