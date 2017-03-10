package com.phonebook.model.services;

import com.phonebook.entities.PhoneNote;
import com.phonebook.model.dao.PhoneNoteDAO;
import com.phonebook.model.dao.impl.JDBCPhoneNote;
import com.phonebook.model.exceptions.DuplicatePhoneNoteDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class NoteOperations {
    @Autowired
    private PhoneNoteDAO phoneNoteDAO;

    public void addNote(PhoneNote phoneNote) throws DuplicatePhoneNoteDataException {
        List<PhoneNote> phoneNoteList = phoneNoteDAO.readAll();
        checkForDuplicateData(phoneNote, phoneNoteList);
        phoneNoteDAO.create(phoneNote);
    }

    public void deleteNote(int noteID) {
        phoneNoteDAO.delete(noteID);
    }

    public void editNote(String firstName, String secondName, PhoneNote phoneNoteNew) throws DuplicatePhoneNoteDataException {
        PhoneNote phoneNoteOld = phoneNoteDAO.readByName(firstName, secondName);
        List<PhoneNote> phoneNoteList = phoneNoteDAO.readAll();

        for (int i = 0; i < phoneNoteList.size(); i++) {
            if ((phoneNoteOld.getFirstName().equals(phoneNoteList.get(i).getFirstName())) && (phoneNoteOld.getSecondName().equals(phoneNoteList.get(i).getSecondName()))) {
                phoneNoteList.remove(i);
            }
        }

        checkForDuplicateData(phoneNoteNew, phoneNoteList);
        phoneNoteDAO.update(phoneNoteNew);
    }

    public List<PhoneNote> filterNotes(String subStr, PhoneNote.noteCompareType noteCompareType){
        List<PhoneNote> phoneNoteList = new ArrayList<>();

        switch (noteCompareType){
            case BY_FIRST_NAME:
                phoneNoteList = phoneNoteDAO.readBySubStrInFirstName(subStr);
                break;

            case BY_SECOND_NAME:
                phoneNoteList = phoneNoteDAO.readBySubStrInSecondName(subStr);
                break;

            case BY_MOBILE_NUMBER:
                phoneNoteList =phoneNoteDAO.readBySubStrInMobileNumber(subStr);
                break;
        }
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
