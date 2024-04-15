package com.Jumong.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("ContactUsers")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private boolean isLoggedIn;
    @DBRef
    private List<Contact> contacts = new ArrayList<Contact>();
}
