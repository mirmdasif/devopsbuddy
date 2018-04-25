package net.asifhossain.devopsbuddy.service;

import net.asifhossain.devopsbuddy.web.domain.frontend.FeedbackPojo;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    /**
     * Sends an email with the content of the feedback pojo.
     * @param feedback
     */
    void sendFeedbackEmail(FeedbackPojo feedback);

    void sendGenericEmailMessage(SimpleMailMessage message);
}
