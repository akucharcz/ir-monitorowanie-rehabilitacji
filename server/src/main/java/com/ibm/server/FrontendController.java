package com.ibm.server;

import com.ibm.server.model.User;
import com.ibm.server.model.ChartStructure;
import com.ibm.server.repository.UserRepository;
import com.ibm.server.service.ChartService;
import com.ibm.server.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class FrontendController {

    private final CustomUserDetailsService userService;
    private final ChartService chartService;
    private final UserRepository userRepository;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");

        }
        return modelAndView;
    }

    @GetMapping("/patients.html")
    public  String patients(Model model) {

        List<User> users = userService.findAllPatients();
        System.out.print("users: "+users);

        model.addAttribute("users", users);

        return "patients";
    }
    @GetMapping("/single_patient/{fullname}")
    public String single_patient(Model model, @PathVariable String fullname) {
        AtomicInteger inte1 = new AtomicInteger(1);
        AtomicInteger inte2 = new AtomicInteger(1);
        AtomicInteger inte3 = new AtomicInteger(1);
        AtomicInteger inte4 = new AtomicInteger(1);

        var user = userService.findUserByName(fullname);

        var hoursData = chartService.getChartData(ChartStructure.builder()
                .login(user.getFullname())
                .period("1")
                .build());

        var daysData = chartService.getChartData(ChartStructure.builder()
                .login(user.getFullname())
                .period("2")
                .build());

        var weeksData = chartService.getChartData(ChartStructure.builder()
                .login(user.getFullname())
                .period("3")
                .build());

        var monthsData = chartService.getChartData(ChartStructure.builder()
                .login(user.getFullname())
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
    @GetMapping("/dashboard.html")
    public String dashboard() {

        return "dashboard";
    }
}
