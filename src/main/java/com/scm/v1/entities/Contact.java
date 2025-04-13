package com.scm.v1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    
@Entity(name="contact")
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        
        @NotNull
        @Column(nullable = false)
        private String name;
    
        @Column(nullable = false, unique = true)
        private String email;
    
        private String phone;
    
        @Column(length = 500)
        private String address;
        

        private Boolean favorite = false;
    
        private String company;
    
        @Enumerated(EnumType.STRING)
        private ContactType contactType = ContactType.PERSONAL;
    
        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;  
}
