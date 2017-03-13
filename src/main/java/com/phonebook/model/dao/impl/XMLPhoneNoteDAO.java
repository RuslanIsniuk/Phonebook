package com.phonebook.model.dao.impl;

import com.phonebook.entities.PhoneNote;
import com.phonebook.model.dao.PhoneNoteList;
import com.phonebook.model.dao.PhoneNoteDAO;
import com.phonebook.parser.Parser;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLPhoneNoteDAO implements PhoneNoteDAO {
    private static final Logger logger = Logger.getLogger(XMLClientDAO.class);
    private PhoneNoteList phoneNoteList;
    private Parser parser;
    private File phoneNoteFile;
    private int noteIDCounter = 0;

    public XMLPhoneNoteDAO(Parser parser, File file) {
        this.parser = parser;
        this.phoneNoteFile = file;

        try {
            if (!phoneNoteFile.exists()) {
                phoneNoteFile.createNewFile();
            }
            phoneNoteList = (PhoneNoteList) parser.getFromFile(phoneNoteFile, PhoneNote.class);
        } catch (IOException ioe) {
            logger.error(ioe);
        } catch (JAXBException je) {
            logger.error(je);
        }

        for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
            if (noteFromList.getNoteID() > noteIDCounter) {
                noteIDCounter = noteFromList.getNoteID();
            }
        }
    }

    @Override
    public PhoneNote read(int noteID) {
        for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
            if (noteID == noteFromList.getNoteID()) {
                return noteFromList;
            }
        }
        return null;
    }

    @Override
    public PhoneNote readByName(String firstName, String secondName) {
        for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
            if ((firstName.equals(noteFromList.getFirstName())) && (secondName.equals(noteFromList.getSecondName()))) {
                return noteFromList;
            }
        }
        return null;
    }

    @Override
    public List<PhoneNote> readByClientID(int clientID) {
        List<PhoneNote> phoneNotes = new ArrayList<>();

        for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
            if (clientID == noteFromList.getNoteOwner().getClientID()) {
                phoneNotes.add(noteFromList);
            }
        }
        return phoneNotes;
    }

    @Override
    public List<PhoneNote> readBySubStrInFirstName(String subStr, int clientID) {
        List<PhoneNote> notesList = new ArrayList<>();
        for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
            if ((clientID == noteFromList.getNoteOwner().getClientID()) && (noteFromList.getFirstName().contains(subStr))) {
                notesList.add(noteFromList);
            }
        }
        return notesList;
    }

    @Override
    public List<PhoneNote> readBySubStrInSecondName(String subStr, int clientID) {
        List<PhoneNote> notesList = new ArrayList<>();
        for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
            if ((clientID == noteFromList.getNoteOwner().getClientID()) && (noteFromList.getSecondName().contains(subStr))) {
                notesList.add(noteFromList);
            }
        }
        return notesList;
    }

    @Override
    public List<PhoneNote> readBySubStrInMobileNumber(String subStr, int clientID) {
        List<PhoneNote> notesList = new ArrayList<>();
        for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
            if ((clientID == noteFromList.getNoteOwner().getClientID()) && (noteFromList.getMobileNumber().contains(subStr))) {
                notesList.add(noteFromList);
            }
        }
        return notesList;
    }

    @Override
    public List<PhoneNote> readAll() {
        return phoneNoteList.getNoteList();
    }

    @Override
    public void create(PhoneNote phoneNote) {
        phoneNote.setNoteID(++noteIDCounter);
        phoneNoteList.addNote(phoneNote);
    }

    @Override
    public void update(PhoneNote phoneNote) {
        for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
            if (phoneNote.getNoteID() == noteFromList.getNoteID()) {
                phoneNoteList.removeNote(noteFromList);
                phoneNoteList.addNote(phoneNote);
            }
        }
    }

    @Override
    public void delete(int noteID) {
        for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
            if (noteID == noteFromList.getNoteID()) {
                phoneNoteList.removeNote(noteFromList);
            }
        }
    }
}
