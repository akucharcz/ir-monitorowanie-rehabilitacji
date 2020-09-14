package com.ibm.server;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.*;

@SpringBootApplication
@RestController
@Controller
@RequiredArgsConstructor
public class api {
    @GetMapping("/")
    public String index() {

        return "index";
    }
    private final LoginController loginController;
    @GetMapping("/login.html")
    public String login() {

        return "login";
    }
    @PostMapping("/login")
    public LoginStructure patientLogin(@RequestBody LoginStructure loginStructure) {
        List<LoginStructure> usersList = loginController.getAllLogins();
        System.out.println(usersList.size());
        for (LoginStructure testowylogin : usersList) {
            if (testowylogin.getLogin().equals(loginStructure.getLogin()) && testowylogin.getPassword().equals(loginStructure.getPassword())) {
                return  testowylogin;
            }
        }
       return loginController.saveLogin(loginStructure);
    }
    private final TrainingController trainingController;

    @PostMapping("/training")
    public TrainingDataStructure postTrainingData(@RequestBody TrainingDataStructure trainingDataStructure) {

        return trainingController.saveTraining(trainingDataStructure);
    }

    @PostMapping("/chart")
    public List<Integer> postChartData(@RequestBody ChartStructure chartStructure) {
        List<TrainingDataStructure> trainings = trainingController.getAllTrainings();
        List<Integer> chart_data = new ArrayList<>();
        String period = chartStructure.getPeriod();
        for (TrainingDataStructure tr : trainings) {
            System.out.println(tr.getLogin());
            LocalDateTime start_date = tr.getStart();
            LocalDateTime now_date = LocalDateTime.now();
            long duration = Duration.between(start_date,now_date).toHours();
            if (period.contains("1")) { // ostatnia godzina
                long MAX_DURATION = HOURS.convert(1, HOURS);
                if (duration > MAX_DURATION) {
                    continue;
                } else {
                    System.out.println("TAK1");
                    chart_data.addAll(tr.getEmgData());
                }
            } else if (period.contains("2")) { // OSTATNIA DOBA
                long MAX_DURATION = HOURS.convert(24, HOURS);
                if (duration > MAX_DURATION) {
                    continue;
                } else {
                    System.out.println("TAK2");
                    chart_data.addAll(tr.getEmgData());
                }
            } else if (period.contains("3")) { //OSTATNI TYDZIEN
                long MAX_DURATION = HOURS.convert(7, DAYS);
                if (duration > MAX_DURATION) {
                    continue;
                } else {
                    System.out.println("TAK3");
                    chart_data.addAll(tr.getEmgData());
                }
            } else if (period.contains("4")) { //OSTATNI MIESIAC
                long MAX_DURATION = HOURS.convert(30, DAYS);
                if (duration > MAX_DURATION) {
                    continue;
                } else {
                    System.out.println("TAK4");
                    chart_data.addAll(tr.getEmgData());
                }
                }
        }
        for (int i = 0; i < chart_data.size(); i++) {
            System.out.println(chart_data.get(i));
        }
        return chart_data;
    }

}