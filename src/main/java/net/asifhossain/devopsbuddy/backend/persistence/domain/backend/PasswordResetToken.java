package net.asifhossain.devopsbuddy.backend.persistence.domain.backend;

import net.asifhossain.devopsbuddy.backend.persistence.converters.LocalDateTimeAttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class PasswordResetToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetToken.class);

    private static final int DEFAULT_TOKEN_LENGTH_IN_MINUTES = 120;

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expiry_date")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime expiryDate;

    public PasswordResetToken() {
    }

    /**
     * @param token Not Null. User Token.
     * @param user Not Null. User for which the token will be created
     * @param creationDateTime The time when the request was made. Not Null.
     * @param expirationInMinutes The length in minutes, for which the token will be valid. Default Value is 2 Hours
     */
    public PasswordResetToken(String token, User user, LocalDateTime creationDateTime, int expirationInMinutes) {

        if (token == null || user == null || creationDateTime == null) {
            throw new IllegalArgumentException("Token, user, expiryDate attribute can not be null");
        }

        this.token = token;
        this.user = user;
        if (expirationInMinutes == 0) {
            LOG.info("The token expiration length in minutes is zero. Assigning default value {}", DEFAULT_TOKEN_LENGTH_IN_MINUTES);
            expirationInMinutes = DEFAULT_TOKEN_LENGTH_IN_MINUTES;
        }

        this.expiryDate = creationDateTime.plusMinutes(expirationInMinutes);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordResetToken that = (PasswordResetToken) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
