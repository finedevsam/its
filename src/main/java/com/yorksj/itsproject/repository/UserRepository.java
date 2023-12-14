package com.yorksj.itsproject.repository;

import com.yorksj.itsproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    User findUserByEmail(String email);

    User findUserById(String Id);

    boolean existsById(String userId);
}
