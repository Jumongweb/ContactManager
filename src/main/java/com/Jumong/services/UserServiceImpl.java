package com.Jumong.services;

import com.Jumong.data.models.User;
import com.Jumong.data.repositories.UserRepository;
import com.Jumong.dtos.request.*;
import com.Jumong.dtos.response.*;
import com.Jumong.exceptions.InvalidPasswordException;
import com.Jumong.exceptions.LoginException;
import com.Jumong.exceptions.UserNotFoundException;
import com.Jumong.exceptions.UsernameAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.Jumong.utils.Mapper.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        String username = registerUserRequest.getUsername();
        validate(username);
        User user = mapRegisterUser(registerUserRequest);
        User savedUser = userRepository.save(user);
        return mapRegisterUserResponse(savedUser);
    }

    public void validate(String username){
        for (User user : userRepository.findAll()){
            if (user.getUsername().equals(username)){
                throw new UsernameAlreadyExistException("Username already exist");
            }
        }
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UserNotFoundException(String.format("%s does not exist", username));
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        if (userRepository.count() < 1) throw new UserNotFoundException("This repository is empty");
        return userRepository.findAll();
    }

    @Override
    public DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest) {
        User user = findByUsername(deleteUserRequest.getUsername());
        userRepository.delete(user);
        return mapDeleteUser(user);
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
        User user = findByUsername(updateUserRequest.getUsername());
        if (!(user.getPassword().equals(updateUserRequest.getPassword()))) {
            throw new InvalidPasswordException("Invalid password");
        }

        user.setUsername(updateUserRequest.getUsername());
        user.setFirstname(updateUserRequest.getFirstName());
        user.setLastname(updateUserRequest.getLastName());
        user.setPassword(updateUserRequest.getPassword());
        //user = mapUpdateUser(updateUserRequest);
        User savedUser = userRepository.save(user);
        //return mapUpdateUser(savedUser);

        UpdateUserResponse response = new UpdateUserResponse();
        response.setMessage("Updated successfully");
        return response;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        validate(username, password);
        User user = findByUsername(username);
        user.setLoggedIn(true);
        User savedUser = userRepository.save(user);
        return mapLoginResponse(savedUser);
    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        String username = logoutRequest.getUsername();
        User user = findByUsername(username);
        if (user.getUsername().equals(username)) user.setLoggedIn(false);
        User savedUser = userRepository.save(user);
        return mapLogoutResponse(savedUser);
    }

    private void validateLogin(String username) {
        User user = userRepository.findByUsername(username);
        if (!(user.isLoggedIn())) throw new LoginException("You need to be logged in to use this service");
    }

    public void validate(String username, String password){
        User user = findByUsername(username);
        if (!(user.getPassword().equals(password))) { throw new InvalidPasswordException("Invalid password");}
    }


}
