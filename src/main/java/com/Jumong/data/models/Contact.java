package com.Jumong.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("Contacts")
public class Contact {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String username;
    private LocalDateTime dateCreated;
}
