package com.Jumong.utils;

import com.Jumong.data.models.Contact;
import com.Jumong.data.models.User;
import com.Jumong.dtos.request.CreateContactRequest;
import com.Jumong.dtos.request.RegisterUserRequest;
import com.Jumong.dtos.request.UpdateUserRequest;
import com.Jumong.dtos.response.*;

public class Mapper {
    public static User mapRegisterUser(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());
        user.setFirstname(registerUserRequest.getFirstname());
        user.setLastname(registerUserRequest.getLastname());
        return user;
    }

    public static RegisterUserResponse mapRegisterUserResponse(User user) {
        RegisterUserResponse response = new RegisterUserResponse();
        response.setMessage("Registered successfully");
        return response;
    }

    public static DeleteUserResponse mapDeleteUser(User user) {
        DeleteUserResponse response = new DeleteUserResponse();
        response.setMessage("Deleted successfully");
        return response;
    }

    public static User mapUpdateUser(UpdateUserRequest updateUserRequest) {
        User user = new User();
        user.setUsername(updateUserRequest.getUsername());
        user.setFirstname(updateUserRequest.getFirstName());
        user.setLastname(updateUserRequest.getLastName());
        user.setPassword(updateUserRequest.getPassword());
        return user;
    }

    public static UpdateUserResponse mapUpdateUser(User user){
        UpdateUserResponse response = new UpdateUserResponse();
        response.setMessage("Updated successfully");
        return response;
    }

    public static LoginResponse mapLoginResponse(User user) {
        LoginResponse response = new LoginResponse();
        response.setMessage("Login successful");
        return response;
    }

    public static LogoutResponse mapLogoutResponse(User user) {
        LogoutResponse response = new LogoutResponse();
        response.setMessage("Logout successful");
        return response;
    }

    public static Contact mapCreateContact(CreateContactRequest createContact) {
        Contact contact = new Contact();
        contact.setFirstname(createContact.getFirstname());
        contact.setLastname(createContact.getLastname());
        contact.setEmail(createContact.getEmail());
        contact.setPhoneNumber(createContact.getPhoneNumber());
        contact.setUsername(createContact.getUsername());
        return contact;
    }

    public static CreateContactResponse mapCreateContactResponse(Contact contact) {
        CreateContactResponse response = new CreateContactResponse();
        response.setMessage("Created successfully");
        return response;
    }

    public static DeleteContactResponse mapDeleteContactResponse(Contact contact) {
        DeleteContactResponse response = new DeleteContactResponse();
        response.setMessage("Deleted successfully");
        return response;
    }

    public static UpdateContactResponse mapUpdateContactResponse(Contact contact){
        UpdateContactResponse response = new UpdateContactResponse();
        response.setMessage("Updated successfully");
        return response;
    }

}
