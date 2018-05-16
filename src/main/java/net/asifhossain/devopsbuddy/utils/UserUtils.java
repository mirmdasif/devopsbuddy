package net.asifhossain.devopsbuddy.utils;

import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.web.controllers.ForgotPasswordController;

import javax.servlet.http.HttpServletRequest;

import static net.asifhossain.devopsbuddy.web.controllers.ForgotPasswordController.CHANGE_PASSWORD_PATH;

public class UserUtils {

    private UserUtils() {
        throw new AssertionError("Non instantiable!");
    }

    public static User createBasicUser(String username, String email) {
        User user = new User();

        user.setUsername(username);
        user.setPassword("password");
        user.setEmail(email);
        user.setFirstName("First");
        user.setLastName("Last");
        user.setPhoneNumber("0123456789");
        user.setCountry("GB");
        user.setEnabled(true);
        user.setDescription("Basic User");
        user.setProfileImageUrl("https://blabla.images.com/username");

        return user;
    }

    public static String createPasswordResetUrl(HttpServletRequest request, long userId, String token) {

        return request.getScheme() +
                "://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() +
                CHANGE_PASSWORD_PATH +
                "?id=" +
                userId +
                "&token=" + token;
    }
}
