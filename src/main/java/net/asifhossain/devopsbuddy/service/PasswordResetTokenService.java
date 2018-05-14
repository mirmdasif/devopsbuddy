package net.asifhossain.devopsbuddy.service;

import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.PasswordResetToken;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.backend.persistence.repository.PasswordResetTokenRepository;
import net.asifhossain.devopsbuddy.backend.persistence.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PasswordResetTokenService {

    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetTokenService.class);

    @Value("${token.expiration.length.minutes}")
    private int expirationTimeInMinutes;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    public PasswordResetToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Transactional
    public PasswordResetToken createPasswordResetTokenForEmail(String email) {

        PasswordResetToken passwordResetToken = null;

        User user = userRepository.findUserByEmail(email);

        if (user != null) {
            String token = UUID.randomUUID().toString();
            LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

            passwordResetToken = tokenRepository.save(new PasswordResetToken(token, user, now, expirationTimeInMinutes));
            LOG.debug("Created password reset token {} for user {}", token, user.getUsername());
        } else {
            LOG.warn("Couldn't find any user with the email {}", email);
        }

        return passwordResetToken;
    }
}
