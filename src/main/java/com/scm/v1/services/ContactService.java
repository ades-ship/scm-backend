package com.scm.v1.services;

import com.scm.v1.dto.ContactDTO;
import com.scm.v1.dto.UserDTO;
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
import java.util.stream.StreamSupport;

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

    public Contact registerContact(String userId, Contact contact) {
        contact.setUserId(userId);
      Contact savedContact = contactRepository.save(contact);

        Optional<User> currentUser = userRepository.findById(userId);
         

        if (currentUser.isPresent()){
           User user=currentUser.get();
           user.getContacts().add(savedContact);
           userRepository.save(user);
        }
            
      return savedContact;
        
    }

    public List<ContactDTO> getAllContacts(String userId) {
        List<Contact> contacts = contactRepository.findByUserId(userId);
        System.out.println("all contact of this user--------" + contacts.size());
        return contacts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ContactDTO updateUser(String contactId, ContactDTO contactDTO) {
        Optional<Contact> contact = contactRepository.findById(contactId);

        if (contact.isPresent()) {
            Contact existing = contact.get();

            if (contactDTO.getName() != null)
                existing.setName(contactDTO.getName());
            if (contactDTO.getEmail() != null)
                existing.setEmail(contactDTO.getEmail());
            if (contactDTO.getPhone() != null)
                existing.setPhone(contactDTO.getPhone());
            if (contactDTO.getAddress() != null)
                existing.setAddress(contactDTO.getAddress());
            if (contactDTO.getCompany() != null)
                existing.setCompany(contactDTO.getCompany());
            if (contactDTO.getContactType() != null)
                existing.setContactType(contactDTO.getContactType());
            if (contactDTO.getFavorite() != null)
                existing.setFavorite(contactDTO.getFavorite());

            Contact updated = contactRepository.save(existing);
            return convertToDTO(updated);
        }

        return new ContactDTO();
    }

    public Boolean deleteUser(String contactId) {
        if (contactRepository.existsById(contactId)) {
            contactRepository.deleteById(contactId);
            return true;
        }
        return false;
    }

    public List<ContactDTO> getFilterContacts(String userId, String query) {
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

    public List<ContactDTO> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        if (contacts.isEmpty()) {
            throw new UnsupportedOperationException("No contacts are added yet'");
        }
        List<ContactDTO> contactdto = StreamSupport.stream(contacts.spliterator(), false)
                .map(contact -> new ContactDTO(contact.getId(), contact.getName(), contact.getEmail(),
                        contact.getPhone(), contact.getAddress(),
                        contact.getCompany(), contact.getContactType(), contact.getFavorite(), null))
                .collect(Collectors.toList());

        return contactdto;

    }

    public ContactDTO getContactDetail(String contactId) {
        Optional<Contact> contact = contactRepository.findById(contactId);
        if (contact.isEmpty()) {
            throw new UnsupportedOperationException("no contact found'");
        }
        return ContactDTO.builder()
                .id(contact.get().getId())
                .name(contact.get().getName())
                .email(contact.get().getEmail())
                .phone(contact.get().getPhone())
                .address(contact.get().getAddress())
                .company(contact.get().getCompany())
                .contactType(contact.get().getContactType())
                .favorite(contact.get().getFavorite())
                .build();

    }
}
