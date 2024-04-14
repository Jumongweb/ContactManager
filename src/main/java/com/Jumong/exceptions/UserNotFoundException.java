package com.Jumong.exceptions;

public class UserNotFoundException extends ContactManagerException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
