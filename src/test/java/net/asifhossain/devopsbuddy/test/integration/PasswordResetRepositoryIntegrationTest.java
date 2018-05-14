package net.asifhossain.devopsbuddy.test.integration;

import net.asifhossain.devopsbuddy.DevopsbuddyApplication;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.PasswordResetToken;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.backend.persistence.repository.PasswordResetTokenRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DevopsbuddyApplication.class)
public class PasswordResetRepositoryIntegrationTest extends AbstractRepositoryIntegrationTest {

    @Value("${token.expiration.length.minutes}")
    private int expirationTimeInMinutes;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Before
    public void init() {
        Assert.assertFalse(expirationTimeInMinutes == 0);
    }

    @Test
    public void testTokenExpirationLength() {
        User user = createUser();

        LocalDateTime now =  LocalDateTime.now(Clock.systemUTC());

        String token = UUID.randomUUID().toString();

        LocalDateTime expectedExpirationTime = now.plusMinutes(expirationTimeInMinutes);

        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, now);

        LocalDateTime actualExpirationTime = passwordResetToken.getExpiryDate();

        Assert.assertNotNull(user);
        Assert.assertEquals(user, passwordResetToken.getUser());
        Assert.assertEquals(expectedExpirationTime, actualExpirationTime);
    }


    @Test
    public void testFindByTokenByValue() {
        User user = createUser();

        Assert.assertNotNull(user);

        LocalDateTime now =  LocalDateTime.now(Clock.systemUTC());

        String token = UUID.randomUUID().toString();

        createPasswordResetToken(token, user, now);

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        Assert.assertNotNull(user);
        Assert.assertNotNull(passwordResetToken);

        Assert.assertEquals(user, passwordResetToken.getUser());
        Assert.assertEquals(passwordResetToken.getToken(), token);
    }

    @Test
    public void testDeleteToken() {
        User user = createUser();

        LocalDateTime now =  LocalDateTime.now(Clock.systemUTC());

        String token = UUID.randomUUID().toString();

        LocalDateTime expectedExpirationTime = now.plusMinutes(expirationTimeInMinutes);

        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, now);

        long tokenId = passwordResetToken.getId();

        passwordResetTokenRepository.delete(tokenId);

        Assert.assertNull(passwordResetTokenRepository.findByToken(token));

    }


    @Test
    public void testCascadeDeleteToken() {
        User user = createUser();

        LocalDateTime now =  LocalDateTime.now(Clock.systemUTC());

        String token = UUID.randomUUID().toString();

        createPasswordResetToken(token, user, now);

        userRepository.delete(user.getId());

        Assert.assertNull(passwordResetTokenRepository.findByToken(token));
    }

    @Test
    public void testMultipleTokensAreReturnedWhenQueryByUserId() {

        User user = createUser();

        LocalDateTime now =  LocalDateTime.now(Clock.systemUTC());

        String token1 = UUID.randomUUID().toString();
        String token2 = UUID.randomUUID().toString();
        String token3 = UUID.randomUUID().toString();

        Set<PasswordResetToken> tokens = new HashSet<>(3);

        tokens.add(new PasswordResetToken(token1, user, now, expirationTimeInMinutes));
        tokens.add(new PasswordResetToken(token2, user, now, expirationTimeInMinutes));
        tokens.add(new PasswordResetToken(token3, user, now, expirationTimeInMinutes));

        passwordResetTokenRepository.save(tokens);

        Set<PasswordResetToken> actualTokens = passwordResetTokenRepository.findAllByUserId(user.getId());

        List<String> actualTokenList = tokens.stream().map(PasswordResetToken::getToken).collect(Collectors.toList());
        List<String> expectedTokensList =
                passwordResetTokenRepository.findAllByUserId(user.getId())
                                            .stream().map(PasswordResetToken::getToken).collect(Collectors.toList());

        Assert.assertEquals(expectedTokensList, actualTokenList);
    }

    private PasswordResetToken createPasswordResetToken(String token, User user, LocalDateTime now) {
        return passwordResetTokenRepository.save(new PasswordResetToken(token, user, now, expirationTimeInMinutes));
    }
}
