package net.asifhossain.devopsbuddy.test.integration;

import net.asifhossain.devopsbuddy.DevopsbuddyApplication;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.enums.PlansEnum;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static net.asifhossain.devopsbuddy.enums.RolesEnum.BASIC;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(DevopsbuddyApplication.class)
public class UserServiceIntegrationTest extends AbstractServiceIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceIntegrationTest.class);

    @Rule
    public TestName testName = new TestName();

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testCreateNewUser() {
        User newUser = createUser(testName);
        Assert.assertNotNull(newUser);
        Assert.assertEquals(newUser.getPlan().getName(), PlansEnum.BASIC.getPlanName());
        Assert.assertTrue(newUser.getUserRoles().size() == 1);
        Assert.assertTrue(newUser.getUserRoles().iterator().next().getRole().getName().equals(BASIC.getRoleName()));
    }

    @Test
    public void testFindUserById() {
        User user = createUser(testName);

        User retrievedUser = userService.findUserById(user.getId());

        Assert.assertNotNull(retrievedUser);
        Assert.assertEquals(user, retrievedUser);
    }

    // Todo: Revisit this logic.
    @Test
    public void testUpdatePassword() {
        User user = createUser(testName);

        String password = UUID.randomUUID().toString();

        userService.updatePassword(user.getId(), password);

        User updatedUser = userService.findUserById(user.getId());

        Assert.assertNotNull(updatedUser);
        Assert.assertTrue(passwordEncoder.matches(password, updatedUser.getPassword()));
    }
}
