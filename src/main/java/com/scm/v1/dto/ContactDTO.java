package com.scm.v1.dto;

import com.scm.v1.entities.ContactType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {
    
        private Long id;

        private String name;
    
        private String email;
    
        private String phone;
    
        private String address;
    
        private String company;
    
        private ContactType contactType;

        private Boolean favorite;

        private Long userId;
        
 
}
