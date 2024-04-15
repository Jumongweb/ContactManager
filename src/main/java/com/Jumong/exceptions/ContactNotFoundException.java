package com.Jumong.exceptions;

public class ContactNotFoundException extends ContactManagerException{

    public ContactNotFoundException(String message) {
        super(message);
    }
}
