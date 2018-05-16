package net.asifhossain.devopsbuddy.web.controllers;

import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.PasswordResetToken;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.service.EmailService;
import net.asifhossain.devopsbuddy.service.I18nService;
import net.asifhossain.devopsbuddy.service.PasswordResetTokenService;
import net.asifhossain.devopsbuddy.service.UserService;
import net.asifhossain.devopsbuddy.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Locale;

import static java.time.Clock.systemUTC;

@Controller
public class ForgotPasswordController {


    private static final Logger LOG = LoggerFactory.getLogger(ForgotPasswordController.class);

    private static final String EMAIL_ADDRESS_VIEW_NAME = "forgotPassword/emailForm";

    private static final String CHANGE_PASSWORD_VIEW_NAME = "forgotPassword/changePassword";

    private static final String EMAIL_MESSAGE_TEXT_PROPERTY_NAME = "forgot.password.email.text";

    private static final String MAIL_SENT_KEY = "mailSent";

    private static final String PASSWORD_RESET_ATTRIBUTE_NAME = "passwordReset";

    private static final String MESSAGE_ATTRIBUTE_NAME = "message";

    public static final String FORGOT_PASSWORD_URL = "/forgotPassword";

    public static final String CHANGE_PASSWORD_URL = "/changePassword";

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private I18nService i18nService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

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


    @RequestMapping(value = CHANGE_PASSWORD_URL, method = RequestMethod.GET)
    public String changePasswordGet(@RequestParam("id") long userId,
                                    @RequestParam("token") String token,
                                    Locale locale,
                                    ModelMap model) {

        String errorMessage = null;

        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);

        if (StringUtils.isEmpty(token) || userId == 0) {

            LOG.error("Invalid user {} or token {} value", userId, token);
            errorMessage = "Invalid user id or token value";

        } else if (passwordResetToken == null) {

            LOG.error("A token can't be found with token value {}", token);
            errorMessage = "Token not found";

        } else if (passwordResetToken.getUser().getId() != userId) {

            LOG.error("The userId passed {} does not match with userId {} associated with token",
                    userId, passwordResetToken.getUser().getId());
            errorMessage = i18nService.getMessage("password.reset.token.invalid", locale);

        } else if (LocalDateTime.now(systemUTC()).isAfter(passwordResetToken.getExpiryDate())) {

            LOG.error("The token {} expired for user {}", passwordResetToken.getUser().getId());
            errorMessage =  i18nService.getMessage("password.reset.token.expired");

        } else {
            model.addAttribute("principalId", userId);
            User user = passwordResetToken.getUser();
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        model.addAttribute(MESSAGE_ATTRIBUTE_NAME, errorMessage);
        
        return CHANGE_PASSWORD_VIEW_NAME;
    }


    @RequestMapping(value = CHANGE_PASSWORD_URL, method = RequestMethod.POST)
    public String changePasswordPost(@RequestParam("password") String password, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (null == auth) {
            LOG.error("An un authorized user tried to invoke the password change request");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "You are not authorized to perform this request");

            return CHANGE_PASSWORD_VIEW_NAME;
        }

        User user = (User) auth.getPrincipal();

        userService.updatePassword(user.getId(), password);
        LOG.info("Password successfully updated for user {}", user.getUsername());

        model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, true);

        return CHANGE_PASSWORD_VIEW_NAME;
    }

}
