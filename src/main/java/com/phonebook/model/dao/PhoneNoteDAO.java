package com.phonebook.model.dao;

import com.phonebook.entities.PhoneNote;

import java.util.List;

/**
 * Created by Руслан on 07.03.2017.
 */
public interface PhoneNoteDAO {
    PhoneNote read(int noteID);

    PhoneNote readByName(String firstName, String secondName);

    List<PhoneNote> readByClientID(int clientID);

    List<PhoneNote> readBySubStrInFirstName(String subStr, int clientID);

    List<PhoneNote> readBySubStrInSecondName(String subStr, int clientID);

    List<PhoneNote> readBySubStrInMobileNumber(String subStr, int clientID);

    List<PhoneNote> readAll();

    void create(PhoneNote phoneNote);

    void update(PhoneNote phoneNote);

    void delete(int noteID);
}
