package com.ibm.server;
import com.ibm.server.model.ChartStructure;
import com.ibm.server.model.ResultStructure;
import com.ibm.server.model.TrainingDataStructure;
import com.ibm.server.model.User;
import com.ibm.server.service.ChartService;
import com.ibm.server.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class api {

    private final CustomUserDetailsService userService;

    @PostMapping("/login")
    public User patientLogin(@RequestBody User loginStructure) {
        List<User> usersList = userService.findAllUsers();
        System.out.println(usersList.size());
        for (User testowylogin : usersList) {
            if (testowylogin.getEmail().equals(loginStructure.getEmail()) && testowylogin.getPassword().equals(loginStructure.getPassword())) {
                return  testowylogin;
            }
        }
       return userService.saveUser(loginStructure);
    }

    private final TrainingController trainingController;

    @PostMapping("/lastResult")
    public ResultStructure postResult(@RequestBody String username) {
        username = username.substring(1, username.length()-1);
        LocalDateTime result = null;
        List<TrainingDataStructure> trainings = trainingController.getAllTrainings();
        LocalDateTime now_date = LocalDateTime.now();
        long minDuration = Duration.between(trainings.get(0).getStart(), now_date).toHours();
        long duration;
        for (TrainingDataStructure tr : trainings) {
            duration = Duration.between(tr.getStart(), now_date).toHours();
            if (tr.getLogin().equals(username)) {
                if (minDuration > duration || minDuration == duration) {
                    minDuration = duration;
                    result = tr.getStart();
                }
            }
        }
        String lastDate = result.toString();
        System.out.println(lastDate);
        ResultStructure rs = new ResultStructure();
        rs.setLastResultDate(result);
        return rs;
    }

    @PostMapping("/registerUser")
    public User patientRegister(@RequestBody User loginStructure) {
        String username = loginStructure.getFullname();
        List<User> usersList = userService.findAllUsers();
        System.out.println(usersList.size());
        for (User testowylogin : usersList) {
            if (Objects.nonNull(testowylogin.getFullname()) && testowylogin.getFullname().equals(username) && testowylogin.getPassword().equals(loginStructure.getPassword())) {
                return  testowylogin;
            }
        }
        return userService.saveUser(loginStructure);
    }

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
