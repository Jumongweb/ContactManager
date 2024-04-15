package com.Jumong.controllers;

import com.Jumong.dtos.request.CreateContactRequest;
import com.Jumong.dtos.response.ApiResponse;
import com.Jumong.dtos.response.CreateContactResponse;
import com.Jumong.exceptions.ContactManagerException;
import com.Jumong.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping("/createContact")
    public ResponseEntity<?> addContact(@RequestBody CreateContactRequest createContactRequest){
        try {
            CreateContactResponse response = contactService.addContact(createContactRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        } catch (ContactManagerException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("findNote/{username}/{firstname}")
    public ResponseEntity<?> findContact(@PathVariable username,
                                         @PathVariable firstname){
        try {
            CreateContactResponse response = contactService.findContactBy(createContactRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        } catch (ContactManagerException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }



}
