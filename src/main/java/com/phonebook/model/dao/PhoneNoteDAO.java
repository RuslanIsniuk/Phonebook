package com.phonebook.model.dao;

import com.phonebook.entities.PhoneNote;

import java.util.List;

/**
 * Created by Руслан on 07.03.2017.
 */
public interface PhoneNoteDAO {
    PhoneNote read(String firstName, String secondName);
    List<PhoneNote> readAll();
    void create(PhoneNote phoneNote);
    void update(String firstName, String secondName,PhoneNote phoneNote);
    void delete(String firstName, String secondName);
}
