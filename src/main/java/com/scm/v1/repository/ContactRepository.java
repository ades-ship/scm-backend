package com.scm.v1.repository;

import com.scm.v1.entities.Contact;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("SELECT c FROM contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") Long userId);

    // @Query("select c from contact where c.user_id = :userId && c.name = query || c.email = query || c.name = phone")
    // List<Contact> getFilterContacts(Long userId, String query);

    @Query("SELECT c FROM contact c WHERE c.user.id = :userId AND (c.name = :query OR c.email = :query OR c.phone = :query)")
    List<Contact> getFilterContacts(Long userId, String query);





}
