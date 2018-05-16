package net.asifhossain.devopsbuddy.test.unit;

import net.asifhossain.devopsbuddy.utils.UserUtils;
import net.asifhossain.devopsbuddy.web.controllers.ForgotPasswordController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.UUID;

import static net.asifhossain.devopsbuddy.web.controllers.ForgotPasswordController.CHANGE_PASSWORD_PATH;

/**
 * @author asif.hossain
 * @since 5/16/18
 */
public class UserUtilsUnitTest {

    private MockHttpServletRequest mockHttpServletRequest;

    @Before
    public void init() {
        mockHttpServletRequest = new MockHttpServletRequest();
    }

    @Test
    public void  testPasswordRestUrlEmailUrlConstruction() {

        mockHttpServletRequest.setServerPort(8080);

        String token = UUID.randomUUID().toString();

        long userId = 11234;

        String expectedUrl = "http://localhost:8080" + CHANGE_PASSWORD_PATH + "?id=" + userId + "&token=" + token;

        String actualUrl = UserUtils.createPasswordResetUrl(mockHttpServletRequest, userId, token);

        Assert.assertEquals(expectedUrl, actualUrl);
    }
}
