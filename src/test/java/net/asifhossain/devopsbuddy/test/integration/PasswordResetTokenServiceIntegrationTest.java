package net.asifhossain.devopsbuddy.test.integration;

import net.asifhossain.devopsbuddy.DevopsbuddyApplication;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.PasswordResetToken;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.service.PasswordResetTokenService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(DevopsbuddyApplication.class)
public class PasswordResetTokenServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    private PasswordResetTokenService tokenService;

    @Rule
    public TestName testName = new TestName();

    @Test
    public void testFindByToken() {
        User user = createUser(testName);
        PasswordResetToken expectedToken = tokenService.createPasswordResetTokenForEmail(user.getEmail());

        PasswordResetToken actualToken = tokenService.findByToken(expectedToken.getToken());

        Assert.assertNotNull(actualToken);
        Assert.assertEquals(expectedToken, actualToken);
    }

    @Test
    public void testCreateTokenForEmail() {
        User user = createUser(testName);
        PasswordResetToken token = tokenService.createPasswordResetTokenForEmail(user.getEmail());

        Assert.assertNotNull(token);
        Assert.assertNotNull(token.getToken());
    }
}
