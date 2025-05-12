package com.scm.v1.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.scm.v1.entities.Contact;
import com.scm.v1.services.ContactService;
import com.scm.v1.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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

    // view user profile
    @GetMapping("/user/{userId}")
    public UserDTO getUser(@PathVariable String userId) {
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
    public UserDTO updateUser(@PathVariable String userId, @RequestBody UserDTO user) {
        return userService.updateUser(userId, user);
    }
    @DeleteMapping("/delete/user/{userId}")
    public Boolean deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }

    // to be doing in future
    // @DeleteMapping("/delete/allusers")
    // public String deleteAllUsers(){
    // }
    
// Contact APIs

    @PostMapping("/contact/register/{userid}")
    public Contact registerNewContact(@PathVariable String userid, @RequestBody Contact contact) {
        return contactService.registerContact(userid, contact);
    }

    // get all contacts
@GetMapping("/allcontacts")
public List<ContactDTO> allContacts(){
    return contactService.getAllContacts();
}

// get contact by contact id;
@GetMapping("/contact/detail/{contactId}")
public ContactDTO getContact(@PathVariable String contactId){
    return contactService.getContactDetail(contactId);
}
    //Get all contact for a specific user
    @GetMapping("/contact/users/{userId}")
    public List<ContactDTO> getContacts(@PathVariable String userId) {
        return contactService.getAllContacts(userId);
    }
    //Get all contact for a specific user based on name, email id and phone number
    @GetMapping("/contact/{userId}")
    public List<ContactDTO> getFilterContacts(@PathVariable String userId, @RequestParam String query) {
        return contactService.getFilterContacts(userId, query);
    }
    @PutMapping("/contact/update/{contactId}")
    public ContactDTO updateContact(@PathVariable String contactId, @RequestBody ContactDTO contact) {
        return contactService.updateUser(contactId, contact);
    }
    @DeleteMapping("/contact/delete/{contactId}")
    public Boolean deleteContact(@PathVariable String contactId) {
        return contactService.deleteUser(contactId);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteAllContacts(@PathVariable String userId) {
        contactService.deleteAllContactsByUserId(userId);
        return ResponseEntity.ok("All contacts for user " + userId + " have been deleted.");
    }
    
}
