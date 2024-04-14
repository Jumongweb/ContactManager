package com.Jumong.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
public class ContactUser {
    @Id
    private String id;
    private String username;
    private String password;
    private String contactName;
    private boolean isLoggedIn;
    @DBRef
    private List<Contact> contactList;
}
