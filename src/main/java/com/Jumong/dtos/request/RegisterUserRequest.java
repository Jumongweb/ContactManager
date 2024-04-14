package com.Jumong.dtos.request;

import lombok.Data;
import lombok.Setter;

@Data
public class RegisterUserRequest {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
}
