package net.asifhossain.devopsbuddy.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @aurhor asifhossain
 * @since 11/23/17.
 */
@Controller
public class HomeController {

    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    public String home() {
        LOG.info("Serving home page");
        return "home";
    }
}
