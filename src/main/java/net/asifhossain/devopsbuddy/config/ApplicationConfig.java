package net.asifhossain.devopsbuddy.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "net.asifhossain.devopsbuddy.backend.persistence.repository")
@EntityScan(basePackages = "net.asifhossain.devopsbuddy.backend.persistence.domain.backend")
@PropertySource("file:///${user.home}/.devopsbuddy/application-common.properties")
public class ApplicationConfig {
}
