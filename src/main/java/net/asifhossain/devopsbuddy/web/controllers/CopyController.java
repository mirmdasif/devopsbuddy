package net.asifhossain.devopsbuddy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  @author mirmdasif
 *  @since January 2018
 */
@Controller
public class CopyController {
    @RequestMapping("/about")
    public String about() {
        return "copy/about";
    }
}
