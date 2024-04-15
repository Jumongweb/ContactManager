package com.Jumong.dtos.request;

import lombok.Data;

@Data
public class UpdateContactRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String username;
}
