package com.ibm.server;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping("/")
    public String index() {

        return "index";
    }
    @GetMapping("/login.html")
    public String login() {

        return "login";
    }
}
