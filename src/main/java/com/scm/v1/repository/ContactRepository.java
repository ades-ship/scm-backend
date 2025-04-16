package com.scm.v1.repository;

import com.scm.v1.entities.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ContactRepository extends MongoRepository<Contact, Long> {

    // Find all contacts by userId
    List<Contact> findByUserId(Long userId);

    // Custom filter query by userId and query matching name, email or phone
    @Query("{ 'userId': ?0, $or: [ { 'name': ?1 }, { 'email': ?1 }, { 'phone': ?1 } ] }")
    List<Contact> getFilterContacts(Long userId, String query);
}
