package net.asifhossain.devopsbuddy.test.integration;

import net.asifhossain.devopsbuddy.DevopsbuddyApplication;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.Plan;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.Role;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.UserRole;
import net.asifhossain.devopsbuddy.backend.persistence.repository.PlanRepository;
import net.asifhossain.devopsbuddy.backend.persistence.repository.RoleRepository;
import net.asifhossain.devopsbuddy.backend.persistence.repository.UserRepository;
import net.asifhossain.devopsbuddy.enums.PlansEnum;
import net.asifhossain.devopsbuddy.enums.RolesEnum;
import net.asifhossain.devopsbuddy.utils.UserUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DevopsbuddyApplication.class)
public class RepositoryIntegrationTests {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PlanRepository planRepository;

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
        User user = createUser();

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
        User user = createUser();

        userRepository.delete(user.getId());

        Assert.assertNull(userRepository.findOne(user.getId()));
    }

    private User createUser() {
        Plan basicPlan = planRepository.save(createBasicPLan(PlansEnum.BASIC));
        Role role = roleRepository.save(createBasicRole(RolesEnum.BASIC));

        User user = UserUtils.createBasicUser();

        user.getUserRoles().add(new UserRole(user, role));
        user.setPlan(basicPlan);

        return userRepository.save(user);
    }

    private Role createBasicRole(RolesEnum role) {
        return new Role(role);
    }

    private Plan createBasicPLan(PlansEnum plansEnum) {
        return new Plan(plansEnum);
    }
}
