package com.scm.v1.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.v1.dto.ContactDTO;
import com.scm.v1.dto.UserDTO;
import com.scm.v1.services.ContactService;
import com.scm.v1.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@RestController
@RequestMapping("/api")
public class SCMContollers {
    
    @Autowired
    UserService userService;
    @Autowired
    ContactService contactService;
    
    //User APIs
    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }
    @GetMapping("/user/{userId}")
    public UserDTO getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }
    @PostMapping("/authenticate/user")
    public UserDTO authenticateUser(@RequestBody UserDTO user) {
        return userService.authenticateUser(user);
    }
    @PostMapping("/register/user")
    public UserDTO registerNewUser(@RequestBody UserDTO user) {
        return userService.registerUser(user);
    }
    @PutMapping("/update/user/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody UserDTO user) {
        return userService.updateUser(userId, user);
    }
    @DeleteMapping("/delete/user/{userId}")
    public Boolean deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
    
    // CONTACT APIs
    // Add contact to a user
    @PostMapping("/contact/register/{userid}")
    public ContactDTO registerNewContact(@PathVariable Long userid, @RequestBody ContactDTO contact) {
        return contactService.registerContact(userid, contact);
    }
    //Get all contact for a specific user
    @GetMapping("/contact/users/{userId}")
    public List<ContactDTO> getContacts(@PathVariable Long userId) {
        return contactService.getAllContacts(userId);
    }
    //Get all contact for a specific user based on name, email id and phone number
    @GetMapping("/contact/{userId}")
    public List<ContactDTO> getFilterContacts(@PathVariable Long userId, @RequestParam String query) {
        return contactService.getFilterContacts(userId, query);
    }
    @PutMapping("/contact/update/{contactId}")
    public ContactDTO updateContact(@PathVariable Long contactId, @RequestBody ContactDTO contact) {
        return contactService.updateUser(contactId, contact);
    }
    @DeleteMapping("/contact/delete/{contactId}")
    public Boolean deleteContact(@PathVariable Long contactId) {
        return contactService.deleteUser(contactId);
    }
    
}
