package net.asifhossain.devopsbuddy.backend.persistence.repository;

import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.PasswordResetToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    @Query("SELECT ptr from PasswordResetToken ptr INNER JOIN ptr.user u WHERE ptr.user.id = ?1")
    Set<PasswordResetToken> findAllByUserId(long userId);
}

