package com.ibm.server;
import com.ibm.server.model.ChartStructure;
import com.ibm.server.model.LoginStructure;
import com.ibm.server.model.TrainingDataStructure;
import com.ibm.server.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.*;

@RestController
@RequiredArgsConstructor
public class api {

    private final LoginController loginController;

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

    private final ChartService chartService;
    @PostMapping("/chart")
    public List<Integer> postChart(@RequestBody ChartStructure chartStructure) {
        return chartService.getChartData(chartStructure);
    }
}
