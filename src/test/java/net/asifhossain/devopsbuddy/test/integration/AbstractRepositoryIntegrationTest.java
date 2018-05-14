package net.asifhossain.devopsbuddy.test.integration;

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
import org.junit.Rule;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepositoryIntegrationTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected PlanRepository planRepository;

    @Rule
    public TestName testName = new TestName();

    User createUser(String username, String email) {

        Plan basicPlan = planRepository.save(createBasicPLan(PlansEnum.BASIC));
        Role role = roleRepository.save(createBasicRole(RolesEnum.BASIC));

        User user = UserUtils.createBasicUser(username, email);

        user.getUserRoles().add(new UserRole(user, role));
        user.setPlan(basicPlan);

        return userRepository.save(user);
    }

    User createUser(TestName testName) {
        return createUser(testName.getMethodName(), testName.getMethodName() + "@gmail.com");
    }

    User createUser() {
        return createUser(testName);
    }

    Role createBasicRole(RolesEnum role) {
        return new Role(role);
    }

    Plan createBasicPLan(PlansEnum plansEnum) {
        return new Plan(plansEnum);
    }
}
