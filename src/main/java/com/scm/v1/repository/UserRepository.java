package com.scm.v1.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.scm.v1.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    
    @Query("{ $and: [ { $or: [ { 'username': ?0 }, { 'email': ?0 } ] }, { 'password': ?1 } ] }")
    Optional<User> authenticateUser(String id, String password);

    User findByUsername(String username);
}
