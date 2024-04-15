package com.Jumong.services;

import com.Jumong.data.models.Contact;
import com.Jumong.data.models.User;
import com.Jumong.data.repositories.ContactRepository;
import com.Jumong.data.repositories.UserRepository;
import com.Jumong.dtos.request.CreateContactRequest;
import com.Jumong.dtos.request.DeleteContactRequest;
import com.Jumong.dtos.request.UpdateContactRequest;
import com.Jumong.dtos.response.CreateContactResponse;
import com.Jumong.dtos.response.DeleteContactResponse;
import com.Jumong.dtos.response.UpdateContactResponse;
import com.Jumong.exceptions.ContactNotFoundException;
import com.Jumong.exceptions.LoginException;
import com.Jumong.exceptions.contactExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.Jumong.utils.Mapper.*;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CreateContactResponse addContact(CreateContactRequest createContactRequest) {
        Contact contact = mapCreateContact(createContactRequest);
        validateContact(createContactRequest.getPhoneNumber());
        User user = userService.findByUsername(createContactRequest.getUsername());
        validateLogin(user.getUsername());
        contactRepository.save(contact);
        List<Contact> userContact = user.getContacts();
        userContact.add(contact);
        user.setContacts(userContact);
        userRepository.save(user);
        return mapCreateContactResponse(contact);
    }

    @Override
    public Long countContacts() {
        return contactRepository.count();
    }

    @Override
    public List<Contact> findAllContactsBelongingToUser(String username) {
        validateLogin(username);
        List<Contact> contacts = new ArrayList<>();
        for (Contact contact : contactRepository.findAll()){
            if (contact.getUsername().equals(username)){
                contacts.add(contact);
            }
        }
        return contacts;
    }

    @Override
    public DeleteContactResponse deleteContactBy(DeleteContactRequest deleteContactRequest) {
        User user = userRepository.findByUsername(deleteContactRequest.getUsername());
        validateLogin(user.getUsername());
        Contact contact = findContactBy(deleteContactRequest.getUsername(), deleteContactRequest.getFirstname());
        contactRepository.delete(contact);
        List<Contact> contacts = user.getContacts();
        contacts.remove(contact);
        user.setContacts(contacts);
        userRepository.save(user);
        return mapDeleteContactResponse(contact);
    }

    @Override
    public UpdateContactResponse updateContactBy(UpdateContactRequest updateContactRequest) {
        validateLogin(updateContactRequest.getUsername());
        User user = userRepository.findByUsername(updateContactRequest.getUsername());
        Contact contact = findContactBy(updateContactRequest.getUsername(), updateContactRequest.getFirstname());
        contact.setFirstname(updateContactRequest.getFirstname());
        contact.setLastname(updateContactRequest.getLastname());
        contact.setPhoneNumber(updateContactRequest.getPhoneNumber());
        contact.setEmail(updateContactRequest.getEmail());
        contact.setUsername(updateContactRequest.getUsername());
        List<Contact> contacts = user.getContacts();
        for (Contact userContact : contacts) {
            if (userContact.getFirstname().equals(updateContactRequest.getFirstname())) {
                contacts.remove(userContact);
                //here
                contactRepository.save(contact);
                // here
                contacts.add(contact);
                user.setContacts(contacts);
                userRepository.save(user);
            }
        }
        /*note.setTitle(updateNoteRequest.getTitle());
        note.setContent(updateNoteRequest.getContent());

        noteRepository.save(note); */
        return mapUpdateContactResponse(contact);
    }

    @Override
    public Contact findContactBy(String username, String firstname) {
        for (Contact contact : findAllContactsBelongingToUser(username)){
            if (contact.getFirstname().equals(firstname)){
                return contact;
            }
        }
        throw new ContactNotFoundException("Contact does not exist");
    }

    private void validateLogin(String username) {
        User user = userRepository.findByUsername(username);
        if (!(user.isLoggedIn())) throw new LoginException("You need to be logged in to use this service");
    }

    private void validateContact(String phoneNumber) {
        for (Contact contact : contactRepository.findAll()){
            if (contact.getPhoneNumber().equals(phoneNumber)) throw new contactExistException("Contact already exist with the same title");
        }
    }



}
