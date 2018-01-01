package net.asifhossain.devopsbuddy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @aurhor asifhossain
 * @since 11/23/17.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }
}
