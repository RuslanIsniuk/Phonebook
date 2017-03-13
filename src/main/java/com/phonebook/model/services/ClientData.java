package com.phonebook.model.services;

import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.entities.PhoneNote;
import com.phonebook.model.dao.PhoneNoteDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientData {
    @Autowired
    private PhoneNoteDAO phoneNoteDAO;

    public List<PhoneNote> getPhonebook(Client client) throws AuthorizationException {
        List<PhoneNote> phoneNoteList = phoneNoteDAO.readByClientID(client.getClientID());
        if (!(phoneNoteList instanceof List)) {
            throw new AuthorizationException();
        }
        return phoneNoteList;
    }

    public PhoneNote getNote(int noteID) throws AuthorizationException {
        PhoneNote phoneNote = phoneNoteDAO.read(noteID);
        if (!(phoneNote instanceof PhoneNote)) {
            throw new AuthorizationException();
        }
        return phoneNote;
    }
}
