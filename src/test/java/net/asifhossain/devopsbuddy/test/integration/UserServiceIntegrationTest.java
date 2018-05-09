package net.asifhossain.devopsbuddy.test.integration;

import net.asifhossain.devopsbuddy.DevopsbuddyApplication;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.Role;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.UserRole;
import net.asifhossain.devopsbuddy.enums.PlansEnum;
import net.asifhossain.devopsbuddy.enums.RolesEnum;
import net.asifhossain.devopsbuddy.service.UserService;
import net.asifhossain.devopsbuddy.utils.UserUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(DevopsbuddyApplication.class)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateNewUser() {
        Set<UserRole> userRoles = new HashSet<>();

        User basicUser = UserUtils.createBasicUser();

        userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));
        User newUser = userService.createUser(basicUser, PlansEnum.BASIC, userRoles);

        Assert.assertNotNull(newUser);

        Assert.assertEquals(newUser.getPlan().getName(), PlansEnum.BASIC.getPlanName());
        Assert.assertTrue(newUser.getUserRoles().size() == 1);
        Assert.assertTrue(newUser.getUserRoles().iterator().next().getRole().getName().equals(RolesEnum.BASIC.getRoleName()));
    }
}
