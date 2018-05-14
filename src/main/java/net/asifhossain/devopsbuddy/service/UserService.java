package net.asifhossain.devopsbuddy.service;

import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.Plan;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.UserRole;
import net.asifhossain.devopsbuddy.backend.persistence.repository.PlanRepository;
import net.asifhossain.devopsbuddy.backend.persistence.repository.RoleRepository;
import net.asifhossain.devopsbuddy.backend.persistence.repository.UserRepository;
import net.asifhossain.devopsbuddy.enums.PlansEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@Transactional(readOnly = true)
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    protected PlanRepository planRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    public User findUserById(long userId) {
        return userRepository.findOne(userId);
    }

    @Transactional
    public User createUser(User user, PlansEnum planEnum, Set<UserRole> userRoles) {

        LOG.debug("Saving User = {}", user);

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        Plan plan = new Plan(planEnum);

        if(!planRepository.exists(plan.getId())) {
            planRepository.save(plan);
        }

        for (UserRole userRole: userRoles) {
            roleRepository.save(userRole.getRole());
        }

        user.setPlan(plan);
        user.getUserRoles().addAll(userRoles);

        return userRepository.save(user);
    }

    @Transactional
    public void updatePassword(long userId, String password) {
        userRepository.updateUserPassword(userId, passwordEncoder.encode(password));
        LOG.debug("Upadted password for user with id =  {}", userId);
        LOG.debug("Upadted password hash {}", passwordEncoder.encode(password));
    }
}
