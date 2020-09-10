package com.ibm.server;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class api {

    private final LoginController loginController;
    private UserService userService;
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

}
