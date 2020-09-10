package com.ibm.server;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SpringBootApplication
//@RestController
@Controller
@RequiredArgsConstructor
public class api {
    @GetMapping("/")
    public String index() {

        return "index";
    }
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