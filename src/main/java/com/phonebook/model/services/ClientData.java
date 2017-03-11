package com.phonebook.model.services;

import com.phonebook.entities.Client;
import com.phonebook.entities.PhoneNote;
import com.phonebook.model.dao.PhoneNoteDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientData {
    @Autowired
    private PhoneNoteDAO phoneNoteDAO;

    public List<PhoneNote> getPhonebook(Client client){
        return phoneNoteDAO.readByClientID(client.getClientID());
    }

    public PhoneNote getNote (int noteID){
        return phoneNoteDAO.read(noteID);
    }
}
