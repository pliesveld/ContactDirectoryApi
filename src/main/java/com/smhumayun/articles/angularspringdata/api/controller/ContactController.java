package com.smhumayun.articles.angularspringdata.api.controller;

import com.smhumayun.articles.angularspringdata.api.model.Contact;
import com.smhumayun.articles.angularspringdata.api.repository.ContactRepository;
import com.smhumayun.articles.angularspringdata.api.repository.ContactSpecification;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by Humayun on 5/24/2016.
 */
@RestController
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping(value = "/api/contacts", method = RequestMethod.GET)
    public Page<Contact> getContacts (
            Pageable pageable
            , @RequestParam(value = "fid", required = false) Long filterId
            , @RequestParam(value = "fn", required = false) String filterName
            , @RequestParam(value = "fpn", required = false) String filterPrimaryNumber
            , @RequestParam(value = "fsn", required = false) String filterSecondaryNumber
            , @RequestParam(value = "fea", required = false) String filterEmailAddress
            , @RequestParam(value = "fuos", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime filterUpdatedOnStart
            , @RequestParam(value = "fuoe", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime filterUpdatedOnEnd
            ) {
        return contactRepository.findAll(ContactSpecification.getFilterSpec(filterId, filterName
                , filterPrimaryNumber, filterSecondaryNumber, filterEmailAddress, filterUpdatedOnStart
                , filterUpdatedOnEnd), pageable);
    }

    @RequestMapping(value = "/api/contacts/{id}", method = RequestMethod.GET)
    public ResponseEntity<Contact> getContact (@PathVariable("id") long id) {
        Contact contact = contactRepository.findOne(id);
        return contact == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/contacts", method = RequestMethod.POST)
    public ResponseEntity<Void> createContact (@RequestBody Contact contact, UriComponentsBuilder uriComponentsBuilder) {
        if(contactRepository.exists(contact.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        contact.setUpdatedOn(new DateTime());
        contact = contactRepository.save(contact);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/contacts/{id}").buildAndExpand(contact.getId()).toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/contacts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Contact> updateContact (@PathVariable("id") long id, @RequestBody Contact contact) {
        Contact serverContact = contactRepository.findOne(id);
        if(serverContact == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        serverContact.setName(contact.getName());
        serverContact.setPrimaryNumber(contact.getPrimaryNumber());
        serverContact.setSecondaryNumber(contact.getSecondaryNumber());
        serverContact.setEmailAddress(contact.getEmailAddress());
        serverContact.setUpdatedOn(contact.getUpdatedOn());
        serverContact = contactRepository.save(serverContact);
        return new ResponseEntity<>(serverContact, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/contacts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Contact> deleteContact (@PathVariable("id") long id) {
        Contact serverContact = contactRepository.findOne(id);
        if(serverContact == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        contactRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
