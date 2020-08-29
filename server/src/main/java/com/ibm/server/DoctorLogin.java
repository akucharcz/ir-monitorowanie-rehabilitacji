package com.ibm.server;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DoctorLogin {
    private LoginRepository loginRepository;
    private UserService userService;
    @PostMapping("/login")
    public String doctorLogin(@ModelAttribute LoginStructure login, Model model) {
        model.addAttribute("login", login);
        try {
            List<LoginStructure> usersList = userService.findAll();
            if (usersList.contains(login)) {
                return "Login accepted";
            }
            return "Login rejected";
        } catch (Exception ex) {
            return "Error getting data";
        }
    }

    @GetMapping("/login")
    public String greetingForm(Model model) {
        model.addAttribute("login", new LoginStructure());
        return "login";
    }
}
