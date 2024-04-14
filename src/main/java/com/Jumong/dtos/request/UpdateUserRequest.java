package com.Jumong.dtos.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
