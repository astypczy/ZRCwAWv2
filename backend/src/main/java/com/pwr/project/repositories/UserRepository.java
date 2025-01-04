package com.pwr.project.repositories;

import com.pwr.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCognitoSub(String cognitoSub);
    Optional<User> findByEmail(String email);
    Optional<User> findByLogin(String login);
    Optional<User> findUserByLogin(String login);

    List<User> findByCognitoSubIsNotNull();
}
