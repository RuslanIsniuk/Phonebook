package com.phonebook.model.dao.impl;

import com.phonebook.entities.PhoneNote;
import com.phonebook.entities.PhoneNoteList;
import com.phonebook.model.dao.PhoneNoteDAO;
import com.phonebook.parser.Parser;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLPhoneNoteDAO implements PhoneNoteDAO {
    private static final Logger logger = Logger.getLogger(XMLClientDAO.class);
    private PhoneNoteList phoneNoteList;
    private Parser parser;
    private File phoneNoteFile;

    public XMLPhoneNoteDAO(Parser parser, File file){
        this.parser = parser;
        this.phoneNoteFile = file;

        try {
            phoneNoteList = (PhoneNoteList) parser.getFromFile(phoneNoteFile, PhoneNoteList.class);
        } catch (JAXBException je) {
            logger.error(je);
        }
    }

    @Override
    public PhoneNote read(int noteID){
        PhoneNote phoneNote = new PhoneNote();
        for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
            if (noteID == noteFromList.getNoteID()) {
                phoneNote = noteFromList;
            }
        }
        return phoneNote;
    }

    @Override
    public PhoneNote readByName(String firstName, String secondName){
        PhoneNote phoneNote = new PhoneNote();
        for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
            if ((firstName.equals(noteFromList.getFirstName()))&&(secondName.equals(noteFromList.getSecondName()))) {
                phoneNote = noteFromList;
            }
        }
        return phoneNote;
    }

    @Override
    public List<PhoneNote> readByClientID(int clientID){
        List<PhoneNote> phoneNotes = new ArrayList<>();
        for(PhoneNote noteFromList:phoneNoteList.getNoteList()){
            if(clientID == noteFromList.getNoteOwner().getClientID()){
                phoneNotes.add(noteFromList);
            }
        }
        return phoneNotes;
    }

    @Override
    public List<PhoneNote> readBySubStrInFirstName(String subStr, int clientID){
        List<PhoneNote> notesList = new ArrayList<>();
        for(PhoneNote noteFromList: phoneNoteList.getNoteList()){
            if((clientID == noteFromList.getNoteOwner().getClientID())&&(noteFromList.getFirstName().contains(subStr))){
                notesList.add(noteFromList);
            }
        }
        return notesList;
    }

    @Override
    public List<PhoneNote> readBySubStrInSecondName(String subStr, int clientID){
        List<PhoneNote> notesList = new ArrayList<>();
        for(PhoneNote noteFromList: phoneNoteList.getNoteList()){
            if((clientID == noteFromList.getNoteOwner().getClientID())&&(noteFromList.getSecondName().contains(subStr))){
                notesList.add(noteFromList);
            }
        }
        return notesList;
    }

    @Override
    public List<PhoneNote> readBySubStrInMobileNumber(String subStr, int clientID){
        List<PhoneNote> notesList = new ArrayList<>();
        for(PhoneNote noteFromList: phoneNoteList.getNoteList()){
            if((clientID == noteFromList.getNoteOwner().getClientID())&&(noteFromList.getMobileNumber().contains(subStr))){
                notesList.add(noteFromList);
            }
        }
        return notesList;
    }

    @Override
    public List<PhoneNote> readAll(){
        return phoneNoteList.getNoteList();
    }

    @Override
    public void create(PhoneNote phoneNote){
        boolean iterate = true;
        int noteID = 1;

        while (iterate) {
            boolean noteInkr = false;

            for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
                if (noteID == noteFromList.getNoteID()) {
                    noteID++;
                    noteInkr = true;
                }
            }

            if (!noteInkr) {
                iterate = false;
            }
        }
        phoneNote.setNoteID(noteID);
        phoneNoteList.addNote(phoneNote);
    }

    @Override
    public void update(PhoneNote phoneNote){
        for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
            if (phoneNote.getNoteID() == noteFromList.getNoteID()) {
                phoneNoteList.removeNote(noteFromList);
                phoneNoteList.addNote(phoneNote);
            }
        }
    }

    @Override
    public void delete(int noteID){
        for (PhoneNote noteFromList : phoneNoteList.getNoteList()) {
            if (noteID == noteFromList.getNoteID()) {
                phoneNoteList.removeNote(noteFromList);
            }
        }
    }
}
