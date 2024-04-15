package com.Jumong.services;

import com.Jumong.data.models.User;
import com.Jumong.dtos.request.*;
import com.Jumong.dtos.response.*;

import java.util.List;


public interface UserService {

    RegisterUserResponse register(RegisterUserRequest registerUserRequest);
    long countUsers();

    User findByUsername(String username);

    List<User> findAllUsers();

    DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest);

    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest);

    LoginResponse login(LoginRequest loginRequest);

    LogoutResponse logout(LogoutRequest logoutRequest);

}
