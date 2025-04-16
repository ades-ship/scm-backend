package com.scm.v1.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

    @Id
    private Long id; // Mongo uses String (ObjectId)

    private String name;

    private String email;

    private String phone;

    private String address;

    private Boolean favorite = false;

    private String company;

    private ContactType contactType = ContactType.PERSONAL;

    @DBRef
    private Long userId;
}
