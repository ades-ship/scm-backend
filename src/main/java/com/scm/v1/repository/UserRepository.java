package com.scm.v1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scm.v1.dto.UserDTO;
import com.scm.v1.entities.Contact;
import com.scm.v1.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Number> {
    public Optional<User> findByUserId(Long userId);

    @Query("SELECT u FROM user u WHERE (u.username = :id OR  u.email = :id) AND u.password = :password")
    public Optional<User> authenticateUser(String id, String password);

    public User findByUsername(String username);

}
 