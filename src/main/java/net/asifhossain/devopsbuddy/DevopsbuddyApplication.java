package net.asifhossain.devopsbuddy;

import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.Plan;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.Role;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.UserRole;
import net.asifhossain.devopsbuddy.backend.persistence.repository.PlanRepository;
import net.asifhossain.devopsbuddy.enums.PlansEnum;
import net.asifhossain.devopsbuddy.enums.RolesEnum;
import net.asifhossain.devopsbuddy.service.PlanService;
import net.asifhossain.devopsbuddy.service.UserService;
import net.asifhossain.devopsbuddy.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DevopsbuddyApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DevopsbuddyApplication.class);

    @Autowired
	private UserService userService;

    @Value("${webmaster.username}")
    private String webmasterUsername;

    @Value("${webmaster.password}")
    private String webmasterPassword;

    @Value("${webmaster.email}")
    private String webmasterEmail;

    @Autowired
	private PlanService planService;

    public static void main(String[] args) {
        LOG.info("Starting devopsbuddy application.");
		SpringApplication.run(DevopsbuddyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

    	LOG.info("Creating a basic and pro plan in the database");
    	planService.createPlan(PlansEnum.BASIC.getId());
    	planService.createPlan(PlansEnum.PRO.getId());

		User basicUser = UserUtils.createBasicUser(webmasterUsername, webmasterEmail);
        basicUser.setPassword(webmasterPassword);

		Set<UserRole> roles = new HashSet<>();
		roles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));

		LOG.debug("Creating user with username = {}", basicUser.getUsername());
		User user = userService.createUser(basicUser, PlansEnum.PRO, roles);
		LOG.debug("Created user with username = {}", user.getUsername());
	}
}
