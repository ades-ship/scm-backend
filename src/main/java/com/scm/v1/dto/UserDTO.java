package com.scm.v1.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scm.v1.entities.Providers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class UserDTO {
   
    private String userId;
    private String username;
    private String email;
    private String password;
    private String about;
    private String phoneNumber;
    private String address;
    private String profilePic;
    private Boolean enabled = false;
    private Boolean emailVerified = false;
    private Boolean phoneVerified = false;
    private Providers providers = Providers.SELF;
    private List<ContactDTO> contacts;

}