package net.asifhossain.devopsbuddy.test.integration;

import net.asifhossain.devopsbuddy.DevopsbuddyApplication;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.Plan;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.Role;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.UserRole;
import net.asifhossain.devopsbuddy.backend.persistence.repository.PlanRepository;
import net.asifhossain.devopsbuddy.backend.persistence.repository.RoleRepository;
import net.asifhossain.devopsbuddy.backend.persistence.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DevopsbuddyApplication.class)
public class RepositoryIntegrationTests {

    private static final int BASIC_PLAN_ID = 1;
    private static final int BASIC_ROLE_ID = 1;

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
        Plan basicPlan = createBasicPLan();
        planRepository.save(basicPlan);

        Plan plan = planRepository.findOne(BASIC_PLAN_ID);

        Assert.assertNotNull(plan);
    }

    @Test
    public void testCreateBasicRole() {
        Role role = createBasicRole();
        roleRepository.save(role);

        Role retrievedRole = roleRepository.findOne(BASIC_ROLE_ID);

        Assert.assertNotNull(retrievedRole);
    }

    @Test
    public void  testCreateBasicUser() {
        Plan plan = planRepository.save(createBasicPLan());
        Role role = roleRepository.save(createBasicRole());
        User user = createBasicUser();

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole();

        userRole.setRole(role);
        userRole.setUser(user);
        userRoles.add(userRole);

        user.getUserRoles().addAll(userRoles);
        user.setPlan(plan);

        user = userRepository.save(user);


        User newUser = userRepository.findOne(user.getId());

        Assert.assertNotNull(newUser);
        Assert.assertTrue(newUser.getId() != 0);

        Assert.assertEquals(newUser.getPlan(), plan);
        Assert.assertTrue(newUser.getUserRoles().contains(userRole));
    }

    private Role createBasicRole() {
        Role role = new Role();
        role.setId(BASIC_ROLE_ID);
        role.setName("ROLE_USER");

        return role;
    }

    private Plan createBasicPLan() {
        Plan plan = new Plan();
        plan.setId(BASIC_PLAN_ID);
        plan.setName("Basic");

        return plan;
    }

    private User createBasicUser() {
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
