package net.asifhossain.devopsbuddy.test.integration;

import net.asifhossain.devopsbuddy.DevopsbuddyApplication;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.Plan;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.Role;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.enums.PlansEnum;
import net.asifhossain.devopsbuddy.enums.RolesEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DevopsbuddyApplication.class)
public class UserRepositoryRepositoryIntegrationTests extends AbstractRepositoryIntegrationTest {

    @Before
    public void init() {
        Assert.assertNotNull(userRepository);
        Assert.assertNotNull(roleRepository);
        Assert.assertNotNull(planRepository);
    }

    @Test
    public void testCreateNewPlan() {
        Plan basicPlan = createBasicPLan(PlansEnum.BASIC);
        planRepository.save(basicPlan);

        Plan plan = planRepository.findOne(PlansEnum.BASIC.getId());

        Assert.assertNotNull(plan);
    }

    @Test
    public void testCreateBasicRole() {
        Role role = createBasicRole(RolesEnum.BASIC);
        roleRepository.save(role);

        Role retrievedRole = roleRepository.findOne(RolesEnum.BASIC.getId());

        Assert.assertNotNull(retrievedRole);
    }

    @Test
    public void  testCreateBasicUser() {

        String username = testName.getMethodName();
        String email = testName.getMethodName() + "@gmail.com";

        User user = createUser(username, email);

        User newUser = userRepository.findOne(user.getId());

        Assert.assertNotNull(newUser);
        Assert.assertTrue(newUser.getId() != 0);

        Assert.assertNotNull(newUser.getUserRoles());
        Assert.assertNotNull(newUser.getPlan());

        Assert.assertEquals(newUser.getPlan(), user.getPlan());
        Assert.assertTrue(newUser.getUserRoles().contains(user.getUserRoles().iterator().next()));
    }

    @Test
    public void testDeleteUser() {

        String username = testName.getMethodName();
        String email = testName.getMethodName() + "@gmail.com";

        User user = createUser(username, email);

        userRepository.delete(user.getId());

        Assert.assertNull(userRepository.findOne(user.getId()));
    }

    @Test
    public void testFindUserByEmail() {
        String username = testName.getMethodName();
        String email = testName.getMethodName() + "@gmail.com";
        createUser(username, email);

        User user = userRepository.findUserByEmail(email);

        Assert.assertNotNull(user);
        Assert.assertEquals(email, user.getEmail());
    }

    @Test
    public void testUpdateUserPassword() {
        User user = createUser(testName);

        String newPassword = UUID.randomUUID().toString();
        userRepository.updateUserPassword(user.getId(), newPassword);

        User updatedUser = userRepository.findOne(user.getId());

        Assert.assertEquals(newPassword, updatedUser.getPassword());
    }
}
