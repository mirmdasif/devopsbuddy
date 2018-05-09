package net.asifhossain.devopsbuddy.utils;

import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;

public class UserUtils {

    private UserUtils() {
        throw new AssertionError("Non instantiable!");
    }

    public static User createBasicUser() {
        User user = new User();

        user.setUsername("user");
        user.setPassword("password");
        user.setFirstName("First");
        user.setLastName("Last");
        user.setEmail("a@b.com");
        user.setPhoneNumber("0123456789");
        user.setCountry("GB");
        user.setEnabled(true);
        user.setDescription("Basic User");
        user.setProfileImageUrl("https://blabla.images.com/username");

        return user;
    }
}
