package net.asifhossain.devopsbuddy.web.controllers;

import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.PasswordResetToken;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.service.EmailService;
import net.asifhossain.devopsbuddy.service.I18nService;
import net.asifhossain.devopsbuddy.service.PasswordResetTokenService;
import net.asifhossain.devopsbuddy.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ForgotPasswordController {


    private static final Logger LOG = LoggerFactory.getLogger(ForgotPasswordController.class);

    private static final String EMAIL_ADDRESS_VIEW_NAME = "forgotPassword/emailForm";

    private static final String MAIL_SENT_KEY = "mailSent";

    public static final String FORGOT_PASSWORD_URL = "/forgotPassword";

    public static final String CHANGE_PASSWORD_PATH = "/changePassword";

    public static final String EMAIL_MESSAGE_TEXT_PROPERTY_NAME = "forgot.password.email.text";

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private I18nService i18nService;

    @Autowired
    private EmailService emailService;

    @Value("${webmaster.email}")
    private String webMasterEmail;

    @RequestMapping(value = FORGOT_PASSWORD_URL, method = RequestMethod.GET)
    public String forgotPasswordGet() {
        return EMAIL_ADDRESS_VIEW_NAME;
    }

    @RequestMapping(value = FORGOT_PASSWORD_URL, method = RequestMethod.POST)
    public String forgotPasswordPost(HttpServletRequest request,
                                     @RequestParam("email") String email,
                                     ModelMap model) {

        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(email);

        if (passwordResetToken == null) {
            LOG.warn("Couldn't find a user for the email =  {}", email);
        } else {

            User user = passwordResetToken.getUser();

            String token = passwordResetToken.getToken();

            String passwordResetUrl = UserUtils.createPasswordResetUrl(request, user.getId(), token);
            LOG.info("Reset Password Url: {}", passwordResetUrl);

            String emailText = i18nService.getMessage(EMAIL_MESSAGE_TEXT_PROPERTY_NAME, request.getLocale());

            SimpleMailMessage simpleMailMessage  = new SimpleMailMessage();
            simpleMailMessage.setFrom(webMasterEmail);
            simpleMailMessage.setTo(user.getEmail());
            simpleMailMessage.setSubject("[Devopsbuddy]: How to Reset Your Password");
            simpleMailMessage.setText(emailText + "\r\n" + passwordResetUrl);

            emailService.sendGenericEmailMessage(simpleMailMessage);

            LOG.info("Sent password reset token to user = {} and email = {}", user.getUsername(), user.getEmail());
        }

        model.addAttribute(MAIL_SENT_KEY, "true");

        return EMAIL_ADDRESS_VIEW_NAME;
    }
}
