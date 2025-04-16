package com.scm.v1.entities;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.*;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private Long userId;

    private String username;

    private String email;

    private String password;

    private String about;

    private String profilePic;

    private Boolean enabled = false;

    private Boolean emailVerified = false;

    private Boolean phoneVerified = false;

    private Roles roles = Roles.USER;

    private Providers providers = Providers.SELF;

    @DBRef
    private List<Contact> contacts = new ArrayList<>();
}
