package net.asifhossain.devopsbuddy.backend.persistence.repository;

import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Returns a user by user name
     * @param username
     * @return a user or null
     */
    public User findUserByUsername(String username);
}
