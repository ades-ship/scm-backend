package com.scm.v1.services;
import com.scm.v1.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.scm.v1.dto.ContactDTO;
import com.scm.v1.entities.Contact;
import com.scm.v1.entities.User;
import com.scm.v1.repository.ContactRepository;

@Service
@CrossOrigin(origins = "http://localhost:3000")
public class ContactService {

    private final UserRepository userRepository;

    @Autowired
    ContactRepository contactRepository;

    ContactService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ContactDTO registerContact(Long userid, ContactDTO newContact){

        Optional<User> currentUser = userRepository.findById(userid);

        if(currentUser.get() == null)return null;

        Contact newContactPending = Contact.builder()
        .name(newContact.getName())
        .email(newContact.getEmail())
        .phone(newContact.getPhone())
        .address(newContact.getAddress())
        .company(newContact.getCompany())
        .contactType(newContact.getContactType())
        .user(currentUser.get())
        .favorite(false)
        .build();

        Contact newContactCreated = contactRepository.save(newContactPending);

        currentUser.get().getContacts().add(newContactCreated);

        return ContactDTO.builder()
        .id(newContactCreated.getId())
        .name(newContactCreated.getName())
        .email(newContactCreated.getEmail())
        .phone(newContactCreated.getPhone())
        .address(newContactCreated.getAddress())
        .company(newContactCreated.getCompany())
        .contactType(newContactCreated.getContactType())
        .userId(newContactCreated.getUser().getUserId())
        .favorite(newContactCreated.getFavorite())
        .build();
    }

    public List<ContactDTO> getAllContacts(Long userId) {
        List<Contact> contacts = contactRepository.findByUserId(userId);

        if(contacts.size() <= 0)return new ArrayList<ContactDTO>();

        return StreamSupport.stream(contacts.spliterator(), false)
                                   .map(contact -> new ContactDTO(contact.getId(), contact.getName(), contact.getEmail(), contact.getPhone(), contact.getAddress(), contact.getCompany(), contact.getContactType(),contact.getFavorite(),  contact.getUser().getUserId()))
                                   .collect(Collectors.toList());
    }

    public ContactDTO updateUser(Long contactId, ContactDTO contactDTO) { // will return empty object if id not found
        
        Optional<Contact> contact = contactRepository.findById(contactId);

        if(contact.isPresent()){
            if (contactDTO.getName() != null) {
                contact.get().setName(contactDTO.getName());
            }
            if (contactDTO.getEmail() != null) {
                contact.get().setEmail(contactDTO.getEmail());
            }
            if (contactDTO.getPhone() != null) {
                contact.get().setPhone(contactDTO.getPhone());
            }
            if (contactDTO.getAddress() != null) {
                contact.get().setAddress(contactDTO.getAddress());
            }
            if (contactDTO.getCompany() != null) {
                contact.get().setCompany(contactDTO.getCompany());
            }
            if (contactDTO.getContactType() != null) {
                contact.get().setContactType(contactDTO.getContactType());
            }
            if (contactDTO.getFavorite() != null) {
                contact.get().setFavorite(contactDTO.getFavorite());
            }
           
            Contact updatedContact = contactRepository.save(contact.get());    
            
            return ContactDTO.builder()
            .id(updatedContact.getId())
            .name(updatedContact.getName())
            .email(updatedContact.getEmail())
            .phone(updatedContact.getPhone())
            .address(updatedContact.getAddress())
            .company(updatedContact.getCompany())
            .contactType(updatedContact.getContactType())
            .favorite(updatedContact.getFavorite())
            .userId(updatedContact.getUser().getUserId())
            .build();
        }
        return new ContactDTO();
    }

    public Boolean deleteUser(Long contactId){
        if(contactRepository.existsById(contactId)){
            contactRepository.deleteById(contactId);
            return true;
        }
        return false;
    }

    public List<ContactDTO> getFilterContacts(Long userId, String query) {

            Pattern pattern = Pattern.compile(query);

            List<Contact> filteredContacts = contactRepository.findByUserId(userId);
            
            return filteredContacts.stream()
            .filter(contact -> {
                Matcher matcherName = pattern.matcher(contact.getName());
                Matcher matcherEmail = pattern.matcher(contact.getEmail());
                Matcher matcherPhone = pattern.matcher(contact.getPhone());
                return matcherName.find() || matcherEmail.find() || matcherPhone.find();
            })
            .map(contact -> 
                ContactDTO.builder() 
                .id(contact.getId())
                .name(contact.getName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .address(contact.getAddress())
                .company(contact.getCompany())
                .contactType(contact.getContactType())
                .userId(contact.getUser().getUserId())
                .favorite(contact.getFavorite())
                .build()
            )
            .collect(Collectors.toList());   
        }

}
