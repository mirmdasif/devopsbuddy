package net.asifhossain.devopsbuddy.service;

import net.asifhossain.devopsbuddy.web.domain.frontend.FeedbackPojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.to.address}")
    private String defaultToAddress;

    /**
     * Prepare a simple mail message from feedback pojo
     * @param feedback
     * @return
     */
    protected SimpleMailMessage prepareSimpleMailMessageFromFeedback(FeedbackPojo feedback) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(defaultToAddress);
        message.setFrom(feedback.getEmail());
        message.setSubject("[Devops Buddy]: Feedback received from " + feedback.getFirstName() + " "
                + feedback.getLastName() + "!");
        message.setText(feedback.getFeedback());

        return message;
    }

    @Override
    public void sendFeedbackEmail(FeedbackPojo feedback) {
        sendGenericEmailMessage(prepareSimpleMailMessageFromFeedback(feedback));
    }
}
