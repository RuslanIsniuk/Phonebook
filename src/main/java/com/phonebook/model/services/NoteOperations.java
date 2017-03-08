package com.phonebook.model.services;

import com.phonebook.entities.PhoneNote;
import com.phonebook.model.dao.PhoneNoteDAO;
import com.phonebook.model.dao.impl.JDBCPhoneNote;
import com.phonebook.model.exceptions.DuplicatePhoneNoteDataException;

import java.util.Collections;
import java.util.List;

/**
 * Created by Руслан on 08.03.2017.
 */
public class NoteOperations {
    private PhoneNoteDAO phoneNoteDAO = new JDBCPhoneNote();

    public void addNote(PhoneNote phoneNote) throws DuplicatePhoneNoteDataException {
        List<PhoneNote> phoneNoteList = phoneNoteDAO.readAll();
        checkForDuplicateData(phoneNote, phoneNoteList);
        phoneNoteDAO.create(phoneNote);
    }

    public void deleteNote(String firstName, String secondName) {
        phoneNoteDAO.delete(firstName, secondName);
    }

    public void editNote(String firstName, String secondName, PhoneNote phoneNoteNew) throws DuplicatePhoneNoteDataException {
        PhoneNote phoneNoteOld = phoneNoteDAO.read(firstName, secondName);
        List<PhoneNote> phoneNoteList = phoneNoteDAO.readAll();

        for (int i = 0; i < phoneNoteList.size(); i++) {
            if ((phoneNoteOld.getFirstName().equals(phoneNoteList.get(i).getFirstName())) && (phoneNoteOld.getSecondName().equals(phoneNoteList.get(i).getSecondName()))) {
                phoneNoteList.remove(i);
            }
        }

        checkForDuplicateData(phoneNoteNew, phoneNoteList);
        phoneNoteDAO.update(firstName, secondName, phoneNoteNew);
    }

    public List<PhoneNote> sortNotesByFirstName (List<PhoneNote> phoneNoteList){
        PhoneNote.noteCompareType = PhoneNote.compareType.BY_FIRST_NAME;
        Collections.sort(phoneNoteList);
        return phoneNoteList;
    }

    public List<PhoneNote> sortNotesBySecondName (List<PhoneNote> phoneNoteList){
        PhoneNote.noteCompareType = PhoneNote.compareType.BY_SECOND_NAME;
        Collections.sort(phoneNoteList);
        return phoneNoteList;
    }

    public List<PhoneNote> sortNotesByMobileNumber (List<PhoneNote> phoneNoteList){
        PhoneNote.noteCompareType = PhoneNote.compareType.BY_MOBILE_NUMBER;
        Collections.sort(phoneNoteList);
        return phoneNoteList;
    }

    private void checkForDuplicateData(PhoneNote phoneNote, List<PhoneNote> phoneNoteList) throws DuplicatePhoneNoteDataException {
        for (PhoneNote phoneNoteFromList : phoneNoteList) {
            if ((phoneNote.getFirstName().equals(phoneNoteFromList.getFirstName())) && (phoneNote.getSecondName().equals(phoneNoteFromList.getSecondName()))) {
                throw new DuplicatePhoneNoteDataException();
            }

            if (phoneNote.getMobileNumber().equals(phoneNoteFromList.getMobileNumber())) {
                throw new DuplicatePhoneNoteDataException();
            }
        }
    }
}
