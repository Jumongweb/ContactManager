package com.Jumong.services;

import com.Jumong.data.models.User;
import com.Jumong.data.repositories.UserRepository;
import com.Jumong.dtos.request.*;
import com.Jumong.dtos.response.LoginResponse;
import com.Jumong.exceptions.InvalidPasswordException;
import com.Jumong.exceptions.UserNotFoundException;
import com.Jumong.exceptions.UsernameAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContactUserServiceImplTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }
    @Test
    public void testThatUserServiceCanSaveUser() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        registerUserRequest.setFirstname("firstName");
        registerUserRequest.setLastname("lastName");
        userService.register(registerUserRequest);
        assertEquals(1, userService.countUsers());
    }

    @Test
    public void testThatUserServiceCannotSaveUserWithTheSameUsername() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstname("firstname");
        registerUserRequest1.setLastname("lastname");
        userService.register(registerUserRequest1);

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUsername("username");
        registerUserRequest2.setPassword("password");
        registerUserRequest2.setFirstname("firstName");
        registerUserRequest2.setLastname("lastName");
        assertThrows(UsernameAlreadyExistException.class, ()-> userService.register(registerUserRequest2));
    }

    @Test
    public void testSave3UserAndUserServiceCountIs3() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstname("firstName");
        registerUserRequest1.setLastname("lastName");
        userService.register(registerUserRequest1);

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUsername("username2");
        registerUserRequest2.setPassword("password");
        registerUserRequest2.setFirstname("firstName");
        registerUserRequest2.setLastname("lastName");
        userService.register(registerUserRequest2);

        RegisterUserRequest registerUserRequest3 = new RegisterUserRequest();
        registerUserRequest3.setUsername("username3");
        registerUserRequest3.setPassword("password");
        registerUserRequest3.setFirstname("firstName");
        registerUserRequest3.setLastname("lastName");
        userService.register(registerUserRequest3);
        assertEquals(3, userService.countUsers());
    }

    @Test
    public void testThatUserServiceCanFindUserByUsername() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("password");
        registerUserRequest.setFirstname("firstName");
        registerUserRequest.setLastname("lastName");
        userService.register(registerUserRequest);
        User user = userService.findByUsername("username");
        assertEquals("username", user.getUsername());
    }

    @Test
    public void testThatUserServiceThrowExceptionIfUserIsNotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.findByUsername("wrongUsername"));
    }

    @Test
    public void testThatUserServiceCanFindAllUsers() {
        RegisterUserRequest userRequest1 = new RegisterUserRequest();
        userRequest1.setUsername("username1");
        userRequest1.setPassword("password");
        userRequest1.setFirstname("firstName");
        userRequest1.setLastname("lastName");
        userService.register(userRequest1);

        RegisterUserRequest userRequest2 = new RegisterUserRequest();
        userRequest2.setUsername("username2");
        userRequest2.setPassword("password");
        userRequest2.setFirstname("firstName");
        userRequest2.setLastname("lastName");
        userService.register(userRequest2);

        RegisterUserRequest userRequest3 = new RegisterUserRequest();
        userRequest3.setUsername("username3");
        userRequest3.setPassword("password");
        userRequest3.setFirstname("firstName");
        userRequest3.setLastname("lastName");
        userService.register(userRequest3);

        User user1 = userService.findByUsername("username1");
        User user2 = userService.findByUsername("username2");
        User user3 = userService.findByUsername("username3");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        assertEquals(users, userService.findAllUsers());
    }

    @Test
    public void testThatUserServiceCanDeleteUser() {
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstname("firstName");
        userRequest.setLastname("lastName");
        userService.register(userRequest);
        assertEquals(1, userService.countUsers());
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUsername("username1");
        userService.deleteUser(deleteUserRequest);
        assertEquals(0, userService.countUsers());
    }

    @Test
    public void testThatUserServiceThrowExceptionIfUserIsNotFoundToDelete() {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUsername("unregisteredUsername");
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(deleteUserRequest));
    }

    @Test
    public void testThatUserServiceCanUpdateUser() {
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstname("firstName");
        userRequest.setLastname("lastName");
        userService.register(userRequest);
        User user1 = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstName("newFirstName");
        updateUserRequest.setLastName("newLastName");
        updateUserRequest.setUsername("username1");
        updateUserRequest.setPassword("password");
        userService.updateUser(updateUserRequest);
        User user = userService.findByUsername("username1");
        assertEquals("newFirstName", user.getFirstname());
        assertEquals("newLastName", user.getLastname());
    }

    @Test
    public void testThatUserServiceCannotUpdateUserWithWrongUsername() {
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstname("firstName");
        userRequest.setLastname("lastName");
        userService.register(userRequest);
        User user1 = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstName("newFirstName");
        updateUserRequest.setLastName("newLastName");
        updateUserRequest.setUsername("newUsername");
        updateUserRequest.setPassword("newPassword");
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(updateUserRequest));
    }

    @Test
    public void testThatUserServiceCannotUpdateUserWithWrongPassword() {
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstname("firstName");
        userRequest.setLastname("lastName");
        userService.register(userRequest);
        User user1 = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstName("newFirstName");
        updateUserRequest.setLastName("newLastName");
        updateUserRequest.setUsername("username1");
        updateUserRequest.setPassword("wrongPassword");
        assertThrows(InvalidPasswordException.class, () -> userService.updateUser(updateUserRequest));
    }

    @Test
    public void testThatUserCanLoginIfTheyAreRegisteredUser() {
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstname("firstName");
        userRequest.setLastname("lastName");
        userService.register(userRequest);

        User user = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userService.login(loginRequest);
        user = userService.findByUsername("username1");
        assertTrue(user.isLoggedIn());
        assertEquals(loginResponse.getMessage(), "Login successful");
    }

    @Test
    public void testThatUserCannotLoginIfTheyAreNotRegisteredUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("unregisteredUser");
        loginRequest.setPassword("password");
        assertThrows(UserNotFoundException.class, () -> userService.login(loginRequest));
    }

    @Test
    public void testThatUserCannotLoginWithWrongPasswordIfTheyAreRegisteredUser() {
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstname("firstName");
        userRequest.setLastname("lastName");
        userService.register(userRequest);

        User user = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("wrongPassword");
        assertThrows(InvalidPasswordException.class, () -> userService.login(loginRequest));
        user = userService.findByUsername("username1");
        assertFalse(user.isLoggedIn());
    }

    @Test
    public void testThatUserCanLogout() {
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstname("firstName");
        userRequest.setLastname("lastName");
        userService.register(userRequest);

        User user = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        user = userService.findByUsername("username1");
        assertTrue(user.isLoggedIn());
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username1");
        userService.logout(logoutRequest);
        user = userService.findByUsername("username1");
        assertFalse(user.isLoggedIn());
    }

    @Test
    public void testThatUserCannotLogoutWithWrongUsername() {
        RegisterUserRequest userRequest = new RegisterUserRequest();
        userRequest.setUsername("username1");
        userRequest.setPassword("password");
        userRequest.setFirstname("firstName");
        userRequest.setLastname("lastName");
        userService.register(userRequest);

        User user = userService.findByUsername("username1");
        assertEquals(1, userService.countUsers());
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        user = userService.findByUsername("username1");
        assertTrue(user.isLoggedIn());
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("wrongUsername");
        assertThrows(UserNotFoundException.class, () -> userService.logout(logoutRequest));
        user = userService.findByUsername("username1");
        assertTrue(user.isLoggedIn());
    }

    @Test
    public void testLoginInThreeDifferentUsers() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstname("firstName");
        registerUserRequest1.setLastname("lastName");
        userService.register(registerUserRequest1);

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUsername("username2");
        registerUserRequest2.setPassword("password");
        registerUserRequest2.setFirstname("firstName");
        registerUserRequest2.setLastname("lastName");
        userService.register(registerUserRequest2);

        RegisterUserRequest registerUserRequest3 = new RegisterUserRequest();
        registerUserRequest3.setUsername("username3");
        registerUserRequest3.setPassword("password");
        registerUserRequest3.setFirstname("firstName");
        registerUserRequest3.setLastname("lastName");
        userService.register(registerUserRequest3);
        assertEquals(3, userService.countUsers());

        LoginRequest loginRequest1 = new LoginRequest();
        LoginRequest loginRequest2 = new LoginRequest();
        LoginRequest loginRequest3 = new LoginRequest();
        loginRequest1.setUsername("username1");
        loginRequest1.setPassword("password");
        loginRequest2.setUsername("username2");
        loginRequest2.setPassword("password");
        loginRequest3.setUsername("username3");
        loginRequest3.setPassword("password");

        userService.login(loginRequest1);
        userService.login(loginRequest2);
        userService.login(loginRequest3);
        assertTrue(userService.findByUsername("username1").isLoggedIn());
        assertTrue(userService.findByUsername("username2").isLoggedIn());
        assertTrue(userService.findByUsername("username3").isLoggedIn());
    }

    @Test
    public void testLoginInThreeDifferentUsersLogoutTheMiddle() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstname("firstName");
        registerUserRequest1.setLastname("lastName");
        userService.register(registerUserRequest1);

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUsername("username2");
        registerUserRequest2.setPassword("password");
        registerUserRequest2.setFirstname("firstName");
        registerUserRequest2.setLastname("lastName");
        userService.register(registerUserRequest2);

        RegisterUserRequest registerUserRequest3 = new RegisterUserRequest();
        registerUserRequest3.setUsername("username3");
        registerUserRequest3.setPassword("password");
        registerUserRequest3.setFirstname("firstName");
        registerUserRequest3.setLastname("lastName");
        userService.register(registerUserRequest3);
        assertEquals(3, userService.countUsers());

        LoginRequest loginRequest1 = new LoginRequest();
        LoginRequest loginRequest2 = new LoginRequest();
        LoginRequest loginRequest3 = new LoginRequest();
        loginRequest1.setUsername("username1");
        loginRequest1.setPassword("password");
        loginRequest2.setUsername("username2");
        loginRequest2.setPassword("password");
        loginRequest3.setUsername("username3");
        loginRequest3.setPassword("password");

        userService.login(loginRequest1);
        userService.login(loginRequest2);
        userService.login(loginRequest3);
        assertTrue(userService.findByUsername("username1").isLoggedIn());
        assertTrue(userService.findByUsername("username2").isLoggedIn());
        assertTrue(userService.findByUsername("username3").isLoggedIn());

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username2");
        userService.logout(logoutRequest);
        assertTrue(userService.findByUsername("username1").isLoggedIn());
        assertFalse(userService.findByUsername("username2").isLoggedIn());
        assertTrue(userService.findByUsername("username3").isLoggedIn());
    }

    @Test
    public void testLoginInThreeDifferentUsersLogoutTheMiddleAndLoginAgain() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstname("firstName");
        registerUserRequest1.setLastname("lastName");
        userService.register(registerUserRequest1);

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUsername("username2");
        registerUserRequest2.setPassword("password");
        registerUserRequest2.setFirstname("firstName");
        registerUserRequest2.setLastname("lastName");
        userService.register(registerUserRequest2);

        RegisterUserRequest registerUserRequest3 = new RegisterUserRequest();
        registerUserRequest3.setUsername("username3");
        registerUserRequest3.setPassword("password");
        registerUserRequest3.setFirstname("firstName");
        registerUserRequest3.setLastname("lastName");
        userService.register(registerUserRequest3);
        assertEquals(3, userService.countUsers());

        LoginRequest loginRequest1 = new LoginRequest();
        LoginRequest loginRequest2 = new LoginRequest();
        LoginRequest loginRequest3 = new LoginRequest();
        loginRequest1.setUsername("username1");
        loginRequest1.setPassword("password");
        loginRequest2.setUsername("username2");
        loginRequest2.setPassword("password");
        loginRequest3.setUsername("username3");
        loginRequest3.setPassword("password");

        userService.login(loginRequest1);
        userService.login(loginRequest2);
        userService.login(loginRequest3);
        assertTrue(userService.findByUsername("username1").isLoggedIn());
        assertTrue(userService.findByUsername("username2").isLoggedIn());
        assertTrue(userService.findByUsername("username3").isLoggedIn());

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username2");
        userService.logout(logoutRequest);
        assertTrue(userService.findByUsername("username1").isLoggedIn());
        assertFalse(userService.findByUsername("username2").isLoggedIn());
        assertTrue(userService.findByUsername("username3").isLoggedIn());

        userService.login(loginRequest2);
        assertTrue(userService.findByUsername("username1").isLoggedIn());
        assertTrue(userService.findByUsername("username2").isLoggedIn());
        assertTrue(userService.findByUsername("username3").isLoggedIn());
    }
}