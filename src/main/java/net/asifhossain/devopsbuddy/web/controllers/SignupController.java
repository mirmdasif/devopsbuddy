package net.asifhossain.devopsbuddy.web.controllers;

import net.asifhossain.devopsbuddy.utils.PlansUtil;
import net.asifhossain.devopsbuddy.web.domain.frontend.ProAccountPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author asif.hossain
 * @since 5/21/18
 */
@Controller
public class SignupController {

    private static final Logger LOG = LoggerFactory.getLogger(SignupController.class);

    private static final String SIGNUP_VIEW_NAME= "registration/signup";

    public static final String PAYLOAD_MODEL_KEY = "payload";

    public static final String SIGNUP_URL_MAPPING = "/signup";

    @RequestMapping(value = SIGNUP_URL_MAPPING, method = RequestMethod.GET)
    public String signupGet(@RequestParam("planId") int planId, ModelMap model) {
        if (!PlansUtil.isValidPlan(planId)) {
            throw new IllegalArgumentException("Plan id not found");
        }

        model.addAttribute(PAYLOAD_MODEL_KEY, new ProAccountPayload());

        return SIGNUP_VIEW_NAME;
    }
}
