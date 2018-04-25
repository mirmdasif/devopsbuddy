package net.asifhossain.devopsbuddy.web.controllers;

import net.asifhossain.devopsbuddy.service.EmailService;
import net.asifhossain.devopsbuddy.web.domain.frontend.FeedbackPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContactController {

    private static final String FEEDBACK_MODEL_KEY = "feedback";
    private static final String CONTACT_US_VIEW_NAME = "contact/contact";

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contactGet(ModelMap model) {

        FeedbackPojo feedbackPojo = new FeedbackPojo();
        model.addAttribute(FEEDBACK_MODEL_KEY, feedbackPojo);

        return CONTACT_US_VIEW_NAME;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public String concatPost(@ModelAttribute(FEEDBACK_MODEL_KEY) FeedbackPojo feedback) {

        log.debug("Feedback POJO Content {}", feedback);
        emailService.sendFeedbackEmail(feedback);

        return CONTACT_US_VIEW_NAME;
    }
}
