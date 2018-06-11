package net.asifhossain.devopsbuddy.config;

import net.asifhossain.devopsbuddy.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import static net.asifhossain.devopsbuddy.web.controllers.ForgotPasswordController.CHANGE_PASSWORD_URL;
import static net.asifhossain.devopsbuddy.web.controllers.ForgotPasswordController.FORGOT_PASSWORD_URL;
import static net.asifhossain.devopsbuddy.web.controllers.SignupController.SIGNUP_URL_MAPPING;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    private final String[] PUBLIC_MATCHERS = {
            "/webjars/**",
            "/css/**",
            "/js/*",
            "/images/**",
            "/",
            "/about/**",
            "/contact/**",
            "/error/**",
            "/console/**",
            "/favicon.ico",
            FORGOT_PASSWORD_URL,
            CHANGE_PASSWORD_URL,
            SIGNUP_URL_MAPPING

    };

    private static final String SALT = "wJR/<jeC9zZT*#*'";


    @Autowired
    private UserSecurityService userSecurityService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        if (activeProfiles.contains("dev")) {
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }

        http
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/payload")
                .failureUrl("/login?error").permitAll()
                .and()
                .logout().permitAll();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userSecurityService)
                .passwordEncoder(passwordEncoder());
    }
}
