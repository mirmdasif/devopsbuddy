package net.asifhossain.devopsbuddy.test.unit;

import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.utils.UserUtils;
import net.asifhossain.devopsbuddy.web.domain.frontend.BasicAccountPayload;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.UUID;

import static net.asifhossain.devopsbuddy.web.controllers.ForgotPasswordController.CHANGE_PASSWORD_URL;

/**
 * @author asif.hossain
 * @since 5/16/18
 */
public class UserUtilsUnitTest {

    private MockHttpServletRequest mockHttpServletRequest;

    private PodamFactory podamFactory;

    @Before
    public void init() {
        mockHttpServletRequest = new MockHttpServletRequest();
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    public void testPasswordRestUrlEmailUrlConstruction() {

        mockHttpServletRequest.setServerPort(8080);

        String token = UUID.randomUUID().toString();

        long userId = 11234;

        String expectedUrl = "http://localhost:8080" + CHANGE_PASSWORD_URL + "?id=" + userId + "&token=" + token;

        String actualUrl = UserUtils.createPasswordResetUrl(mockHttpServletRequest, userId, token);

        Assert.assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testMapBasicUserPayloadToUserEntity() {

        BasicAccountPayload userPayload = podamFactory.manufacturePojoWithFullData(BasicAccountPayload.class);
        userPayload.setEmail("test@gmail.com");

        User user  = UserUtils.formUserPayloadToDomainUser(userPayload);

        Assert.assertEquals(userPayload.getUsername(), user.getUsername());
        Assert.assertEquals(userPayload.getPassword(), user.getPassword());
        Assert.assertEquals(userPayload.getEmail(), user.getEmail());
        Assert.assertEquals(userPayload.getFirstName(), user.getFirstName());
        Assert.assertEquals(userPayload.getLastName(), user.getLastName());
        Assert.assertEquals(userPayload.getPhoneNumber(), user.getPhoneNumber());
        Assert.assertEquals(userPayload.getCountry(), user.getCountry());
        Assert.assertEquals(userPayload.getDescription(), user.getDescription());
    }

}
