package com.phonebook.model.services;

import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.entities.PhoneNote;
import com.phonebook.model.dao.PhoneNoteDAO;
import com.phonebook.model.exceptions.DuplicatePhoneNoteDataException;
import com.phonebook.model.exceptions.PhoneNoteDataIncorrectException;
import com.phonebook.model.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoteOperations {
    @Autowired
    private PhoneNoteDAO phoneNoteDAO;

    private static final String FIRST_NAME_FIELD = "[^\\s]{4,45}";
    private static final String SECOND_NAME_FIELD = "[^\\s]{4,45}";
    private static final String ADDITIONAL_NAME_FIELD = "[^\\s]{4,45}";
    private static final String MOB_NUMBER_FIELD = "[\\u002B][3][8][0](([3][9])||([5][0])||([4][4])||([9][1-9])||([6][3-8]))\\d{7}";
    private static final String HOME_NUMBER_FIELD = "([\\s])||([\\u002B][3][8][0][4][4]\\d{7})";
    private static final String LOCATION_FIELD = "([\\s])||(.{4,45})";
    private static final String EMAIL_FIELD = "([\\s])||(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$)";
    private static final String ERROR_MESSAGE = "Error! Nothing found!";

    public void addNote(PhoneNote phoneNote, Client client) throws ServiceException, AuthorizationException {
        List<PhoneNote> phoneNoteList = phoneNoteDAO.readAll();
        if (!(phoneNoteList instanceof List)) {
            throw new AuthorizationException();
        }
        checkForCorrectInput(phoneNote);
        checkForDuplicateData(phoneNote, phoneNoteList);
        phoneNote.setNoteOwner(client);
        phoneNoteDAO.create(phoneNote);
    }

    public void deleteNote(int noteID) {
        phoneNoteDAO.delete(noteID);
    }

    public void editNote(int noteIDOld, PhoneNote phoneNoteNew) throws ServiceException, AuthorizationException {
        PhoneNote phoneNoteOld = phoneNoteDAO.read(noteIDOld);
        if (!(phoneNoteOld instanceof PhoneNote)) {
            throw new AuthorizationException();
        }

        List<PhoneNote> phoneNoteList = phoneNoteDAO.readAll();
        fillEmptyFields(phoneNoteNew, phoneNoteOld);
        checkForCorrectInput(phoneNoteNew);

        for (int i = 0; i < phoneNoteList.size(); i++) {
            if ((phoneNoteOld.getFirstName().equals(phoneNoteList.get(i).getFirstName())) && (phoneNoteOld.getSecondName().equals(phoneNoteList.get(i).getSecondName()))) {
                phoneNoteList.remove(i);
            }
        }

        checkForDuplicateData(phoneNoteNew, phoneNoteList);
        phoneNoteDAO.update(phoneNoteNew);
    }

    public List<PhoneNote> filterNotes(String subStr, int clientID, PhoneNote.noteCompareType noteCompareType) throws ServiceException {
        List<PhoneNote> phoneNoteList = new ArrayList<>();

        switch (noteCompareType) {
            case BY_FIRST_NAME:
                phoneNoteList = phoneNoteDAO.readBySubStrInFirstName(subStr, clientID);
                if (!(phoneNoteList instanceof List)) {
                    throw new PhoneNoteDataIncorrectException(ERROR_MESSAGE);
                }
                break;

            case BY_SECOND_NAME:
                phoneNoteList = phoneNoteDAO.readBySubStrInSecondName(subStr, clientID);
                if (!(phoneNoteList instanceof List)) {
                    throw new PhoneNoteDataIncorrectException(ERROR_MESSAGE);
                }
                break;

            case BY_MOBILE_NUMBER:
                phoneNoteList = phoneNoteDAO.readBySubStrInMobileNumber(subStr, clientID);
                if (!(phoneNoteList instanceof List)) {
                    throw new PhoneNoteDataIncorrectException(ERROR_MESSAGE);
                }
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

    private void fillEmptyFields(PhoneNote phoneNoteNew, PhoneNote phoneNoteOld) {
        if (("").equals(phoneNoteNew.getFirstName())) {
            phoneNoteNew.setFirstName(phoneNoteOld.getFirstName());
        }

        if (("").equals(phoneNoteNew.getSecondName())) {
            phoneNoteNew.setSecondName(phoneNoteOld.getSecondName());
        }

        if (("").equals(phoneNoteNew.getAdditionalName())) {
            phoneNoteNew.setAdditionalName(phoneNoteOld.getAdditionalName());
        }

        if (("").equals(phoneNoteNew.getMobileNumber())) {
            phoneNoteNew.setMobileNumber(phoneNoteOld.getMobileNumber());
        }
    }

    private void checkForCorrectInput(PhoneNote phoneNote) throws PhoneNoteDataIncorrectException {
        if (!compileStr(phoneNote.getFirstName(), FIRST_NAME_FIELD)) {
            throw new PhoneNoteDataIncorrectException("ERROR! First name is incorrect!");
        }

        if (!compileStr(phoneNote.getSecondName(), SECOND_NAME_FIELD)) {
            throw new PhoneNoteDataIncorrectException("ERROR! Second name is incorrect!");
        }

        if (!compileStr(phoneNote.getAdditionalName(), ADDITIONAL_NAME_FIELD)) {
            throw new PhoneNoteDataIncorrectException("ERROR! Additional name is incorrect!");
        }

        if (!compileStr(phoneNote.getMobileNumber(), MOB_NUMBER_FIELD)) {
            throw new PhoneNoteDataIncorrectException("ERROR! Mobile number is incorrect!");
        }

        if (!compileStr(phoneNote.getHomeNumber(), HOME_NUMBER_FIELD)) {
            throw new PhoneNoteDataIncorrectException("ERROR! Home number is incorrect!");
        }

        if (!compileStr(phoneNote.getLocation(), LOCATION_FIELD)) {
            throw new PhoneNoteDataIncorrectException("ERROR! Location is incorrect!");
        }

        if (!compileStr(phoneNote.getEmail(), EMAIL_FIELD)) {
            throw new PhoneNoteDataIncorrectException("ERROR! Email is incorrect!");
        }

    }

    private boolean compileStr(String checkedObject, String compileStr) {
        Pattern pattern = Pattern.compile(compileStr);
        Matcher matcher = pattern.matcher(checkedObject);
        return matcher.matches();
    }

}
