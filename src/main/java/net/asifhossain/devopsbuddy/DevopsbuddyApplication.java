package net.asifhossain.devopsbuddy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "net.asifhossain.devopsbuddy.backend.persistence.repository")
public class DevopsbuddyApplication {

    private static final Logger LOG = LoggerFactory.getLogger(DevopsbuddyApplication.class);

	public static void main(String[] args) {
        LOG.info("Starting devopsbuddy application.");
		SpringApplication.run(DevopsbuddyApplication.class, args);
	}
}
