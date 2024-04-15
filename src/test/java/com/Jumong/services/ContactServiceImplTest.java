package com.Jumong.services;

import com.Jumong.data.models.Contact;
import com.Jumong.data.repositories.ContactRepository;
import com.Jumong.data.repositories.UserRepository;
import com.Jumong.dtos.request.*;
import com.Jumong.exceptions.ContactNotFoundException;
import com.Jumong.exceptions.LoginException;
import com.Jumong.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContactServiceImplTest {
    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        contactRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testThatContactServiceCanAddContactIfUserExist() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstname("firstName");
        registerUserRequest1.setLastname("lastName");
        userService.register(registerUserRequest1);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setFirstname("firstname");
        createContactRequest.setLastname("lastname");
        createContactRequest.setUsername("username1");
        createContactRequest.setEmail("jay@gmail.com");
        createContactRequest.setPhoneNumber("0123456789");
        contactService.addContact(createContactRequest);
        assertEquals(1, contactService.countContacts());
        assertEquals(1, userService.findByUsername("username1").getContacts().size());
    }

    @Test
    public void testThatContactServiceCannotAddContactIfUserDoesNotExist() {
        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setFirstname("firstname");
        createContactRequest.setLastname("lastname");
        createContactRequest.setUsername("username1");
        createContactRequest.setEmail("jay@gmail.com");
        createContactRequest.setPhoneNumber("0123456789");
        assertThrows(UserNotFoundException.class, ()->contactService.addContact(createContactRequest));
        assertEquals(0, contactService.countContacts());
    }
        @Test
    public void testThatContactServiceCannotAddContactIfUserIsNotLoggedIn() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstname("firstName");
        registerUserRequest1.setLastname("lastName");
        userService.register(registerUserRequest1);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setFirstname("firstname");
        createContactRequest.setLastname("lastname");
        createContactRequest.setUsername("username1");
        createContactRequest.setEmail("jay@gmail.com");
        createContactRequest.setPhoneNumber("0123456789");
        assertThrows(LoginException.class, ()->contactService.addContact(createContactRequest));
        assertEquals(0, contactService.countContacts());
        assertEquals(0, userService.findByUsername("username1").getContacts().size());
    }

    @Test
    public void testThatContactServiceCanFindContact() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstname("firstName");
        registerUserRequest1.setLastname("lastName");
        userService.register(registerUserRequest1);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setFirstname("firstname");
        createContactRequest.setLastname("lastname");
        createContactRequest.setUsername("username1");
        createContactRequest.setEmail("jay@gmail.com");
        createContactRequest.setPhoneNumber("0123456789");
        contactService.addContact(createContactRequest);
        assertEquals(1, contactService.countContacts());
        assertEquals(1, userService.findByUsername("username1").getContacts().size());
        Contact foundContact = contactService.findContactBy("username1", "firstname");
        assertEquals("0123456789", foundContact.getPhoneNumber());
    }

    @Test
    public void testThatContactServiceThrowExceptionIfContactThatDoesNotExistIsSearchedFor() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstname("firstName");
        registerUserRequest1.setLastname("lastName");
        userService.register(registerUserRequest1);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setFirstname("firstname");
        createContactRequest.setLastname("lastname");
        createContactRequest.setUsername("username1");
        createContactRequest.setEmail("jay@gmail.com");
        createContactRequest.setPhoneNumber("0123456789");
        contactService.addContact(createContactRequest);
        assertEquals(1, contactService.countContacts());
        assertEquals(1, userService.findByUsername("username1").getContacts().size());
        assertThrows(ContactNotFoundException.class, ()->contactService.findContactBy("username1", "wrongName"));
    }

    @Test
    public void testThatContactServiceCanDeleteContact() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstname("firstName");
        registerUserRequest1.setLastname("lastName");
        userService.register(registerUserRequest1);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setFirstname("firstname");
        createContactRequest.setLastname("lastname");
        createContactRequest.setUsername("username1");
        createContactRequest.setEmail("jay@gmail.com");
        createContactRequest.setPhoneNumber("0123456789");
        contactService.addContact(createContactRequest);
        assertEquals(1, contactService.countContacts());
        assertEquals(1, userService.findByUsername("username1").getContacts().size());
        Contact foundContact = contactService.findContactBy("username1", "firstname");

        DeleteContactRequest deleteContactRequest = new DeleteContactRequest();
        deleteContactRequest.setFirstname("firstname");
        deleteContactRequest.setUsername("username1");
        contactService.deleteContactBy(deleteContactRequest);
        assertEquals(0, contactService.countContacts());
        assertEquals(0, userService.findByUsername("username1").getContacts().size());
    }

    @Test
    public void testThatContactServiceThrowExceptionIfIDeleteContactThatDoesNotExist() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstname("firstName");
        registerUserRequest1.setLastname("lastName");
        userService.register(registerUserRequest1);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setFirstname("firstname");
        createContactRequest.setLastname("lastname");
        createContactRequest.setUsername("username1");
        createContactRequest.setEmail("jay@gmail.com");
        createContactRequest.setPhoneNumber("0123456789");
        contactService.addContact(createContactRequest);
        assertEquals(1, contactService.countContacts());
        assertEquals(1, userService.findByUsername("username1").getContacts().size());
        Contact foundContact = contactService.findContactBy("username1", "firstname");

        DeleteContactRequest deleteContactRequest = new DeleteContactRequest();
        deleteContactRequest.setFirstname("wrongName");
        deleteContactRequest.setUsername("username1");
        assertThrows(ContactNotFoundException.class, ()->contactService.deleteContactBy(deleteContactRequest));
        assertEquals(1, contactService.countContacts());
        assertEquals(1, userService.findByUsername("username1").getContacts().size());
    }

    @Test
    public void testThatContactServiceCanUpdateContact() {
        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("username1");
        registerUserRequest1.setPassword("password");
        registerUserRequest1.setFirstname("firstName");
        registerUserRequest1.setLastname("lastName");
        userService.register(registerUserRequest1);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        userService.login(loginRequest);

        CreateContactRequest createContactRequest = new CreateContactRequest();
        createContactRequest.setFirstname("firstname");
        createContactRequest.setLastname("lastname");
        createContactRequest.setUsername("username1");
        createContactRequest.setEmail("jay@gmail.com");
        createContactRequest.setPhoneNumber("0123456789");
        contactService.addContact(createContactRequest);
        assertEquals(1, contactService.countContacts());
        assertEquals(1, userService.findByUsername("username1").getContacts().size());

        UpdateContactRequest updateContactRequest = new UpdateContactRequest();
        updateContactRequest.setUsername("username1");
        updateContactRequest.setFirstname("firstname");
        updateContactRequest.setLastname("updatedLastname");
        updateContactRequest.setPhoneNumber("0123456789");
        updateContactRequest.setEmail("updatedJay@gmail.com");
        contactService.updateContactBy(updateContactRequest);
        Contact foundContact = contactService.findContactBy("username1", "firstname");
        assertEquals("updatedJay@gmail.com", foundContact.getEmail());
        assertEquals("updatedLastname", foundContact.getLastname());
        assertEquals(1, userService.findByUsername("username1").getContacts().size());
    }


}