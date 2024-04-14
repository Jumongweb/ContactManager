package com.Jumong.exceptions;

public class UsernameAlreadyExistException extends ContactManagerException{

    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
