package com.ibm.server;
import com.ibm.server.model.ChartStructure;
import com.ibm.server.model.TrainingDataStructure;
import com.ibm.server.model.User;
import com.ibm.server.service.ChartService;
import com.ibm.server.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class api {

    private final CustomUserDetailsService userService;
    private final TrainingController trainingController;

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

    @PostMapping("/registerUser")
    public User patientRegister(@RequestBody User loginStructure) {
        List<User> usersList = userService.findAllUsers();
        System.out.println(usersList.size());
        for (User testowylogin : usersList) {
            if (Objects.nonNull(testowylogin.getFullname()) && testowylogin.getFullname().equals(loginStructure.getFullname()) && testowylogin.getPassword().equals(loginStructure.getPassword())) {
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
