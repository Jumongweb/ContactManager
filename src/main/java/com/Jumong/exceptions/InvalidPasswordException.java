package com.Jumong.exceptions;

public class InvalidPasswordException extends ContactManagerException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
