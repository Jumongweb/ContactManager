package com.Jumong.services;

import com.Jumong.data.models.Contact;
import com.Jumong.dtos.request.CreateContactRequest;
import com.Jumong.dtos.request.DeleteContactRequest;
import com.Jumong.dtos.request.UpdateContactRequest;
import com.Jumong.dtos.response.CreateContactResponse;
import com.Jumong.dtos.response.DeleteContactResponse;
import com.Jumong.dtos.response.UpdateContactResponse;

import java.util.List;

public interface ContactService {
    CreateContactResponse addContact(CreateContactRequest createContactRequest);

    Long countContacts();

    Contact findContactBy(String username, String firstname);
    List<Contact> findAllContactsBelongingToUser(String username);

    DeleteContactResponse deleteContactBy(DeleteContactRequest deleteContactRequest);

    UpdateContactResponse updateContactBy(UpdateContactRequest updateContactRequest);

}
