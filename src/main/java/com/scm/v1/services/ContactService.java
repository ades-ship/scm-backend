package com.scm.v1.services;

import com.scm.v1.dto.ContactDTO;
import com.scm.v1.entities.Contact;
import com.scm.v1.entities.User;
import com.scm.v1.repository.ContactRepository;
import com.scm.v1.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@CrossOrigin(origins = "http://localhost:3000")
public class ContactService {

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(UserRepository userRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    public ContactDTO registerContact(Long userId, ContactDTO newContact) {
        Optional<User> currentUser = userRepository.findById(userId);

        if (currentUser.isEmpty()) return null;

        Contact contactToSave = Contact.builder()
                .name(newContact.getName())
                .email(newContact.getEmail())
                .phone(newContact.getPhone())
                .address(newContact.getAddress())
                .company(newContact.getCompany())
                .contactType(newContact.getContactType())
                .userId(newContact.getUserId())
                .favorite(false)
                .build();

        Contact saved = contactRepository.save(contactToSave);
        return convertToDTO(saved);
    }

    public List<ContactDTO> getAllContacts(Long userId) {
        List<Contact> contacts = contactRepository.findByUserId(userId);
        return contacts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ContactDTO updateUser(Long contactId, ContactDTO contactDTO) {
        Optional<Contact> contact = contactRepository.findById(contactId);

        if (contact.isPresent()) {
            Contact existing = contact.get();

            if (contactDTO.getName() != null) existing.setName(contactDTO.getName());
            if (contactDTO.getEmail() != null) existing.setEmail(contactDTO.getEmail());
            if (contactDTO.getPhone() != null) existing.setPhone(contactDTO.getPhone());
            if (contactDTO.getAddress() != null) existing.setAddress(contactDTO.getAddress());
            if (contactDTO.getCompany() != null) existing.setCompany(contactDTO.getCompany());
            if (contactDTO.getContactType() != null) existing.setContactType(contactDTO.getContactType());
            if (contactDTO.getFavorite() != null) existing.setFavorite(contactDTO.getFavorite());

            Contact updated = contactRepository.save(existing);
            return convertToDTO(updated);
        }

        return new ContactDTO();
    }

    public Boolean deleteUser(Long contactId) {
        if (contactRepository.existsById(contactId)) {
            contactRepository.deleteById(contactId);
            return true;
        }
        return false;
    }

    public List<ContactDTO> getFilterContacts(Long userId, String query) {
        Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
        List<Contact> contacts = contactRepository.findByUserId(userId);

        return contacts.stream()
                .filter(contact -> pattern.matcher(contact.getName()).find()
                        || pattern.matcher(contact.getEmail()).find()
                        || pattern.matcher(contact.getPhone()).find())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ContactDTO convertToDTO(Contact contact) {
        return ContactDTO.builder()
                .id(contact.getId())
                .name(contact.getName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .address(contact.getAddress())
                .company(contact.getCompany())
                .contactType(contact.getContactType())
                .userId(contact.getUserId())
                .favorite(contact.getFavorite())
                .build();
    }
}
