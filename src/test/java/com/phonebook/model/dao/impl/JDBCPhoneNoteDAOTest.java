package com.phonebook.model.dao.impl;

import com.phonebook.entities.Client;
import com.phonebook.entities.PhoneNote;
import com.phonebook.model.dao.ClientDAO;
import com.phonebook.model.dao.PhoneNoteDAO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class JDBCPhoneNoteDAOTest {
    @Autowired
    private  ClientDAO clientDAO;
    @Autowired
    private  PhoneNoteDAO phoneNoteDAO;
    private static Client client = new Client();
    private static PhoneNote phoneNote = new PhoneNote();
    private static PhoneNote phoneNote2 = new PhoneNote();

    @BeforeClass
    public static void setClientData() throws Exception {
        client.setClientLogin("testLogin");
        client.setClientPass("testPass");
        client.setClientFullName("testFullName");

        phoneNote.setFirstName("testFName");
        phoneNote.setSecondName("testSName");
        phoneNote.setAdditionalName("testAName");
        phoneNote.setMobileNumber("testMobNum");
        phoneNote.setHomeNumber("testHNum");
        phoneNote.setLocation("testLocation");
        phoneNote.setEmail("testEmail");

        phoneNote2.setFirstName("testFName2");
        phoneNote2.setSecondName("testSName2");
        phoneNote2.setAdditionalName("testAName2");
        phoneNote2.setMobileNumber("testMobNum2");
        phoneNote2.setHomeNumber("testHNum2");
        phoneNote2.setLocation("testLocation2");
        phoneNote2.setEmail("testEmail2");


    }
    private void createObj(){
        clientDAO.create(client);
        phoneNote.setNoteOwner(client);
        phoneNote2.setNoteOwner(client);
        phoneNoteDAO.create(phoneNote);
        phoneNoteDAO.create(phoneNote2);
    }

    private void deleteObj(){
        phoneNoteDAO.delete(phoneNote.getNoteID());
        phoneNoteDAO.delete(phoneNote2.getNoteID());
        clientDAO.delete(client.getClientID());
    }

    @Test
    public void testReadByName() {
        createObj();
        PhoneNote phoneNote1 = phoneNoteDAO.readByName(phoneNote.getFirstName(), phoneNote.getSecondName());
        deleteObj();
        assertEquals(phoneNote, phoneNote1);
    }

    @Test
    public void testReadBySubStrInFirstName() {
        createObj();
        List<PhoneNote> phoneNotes = phoneNoteDAO.readBySubStrInFirstName("testF",client.getClientID());
        deleteObj();
        assertEquals(phoneNote, phoneNotes.get(0));
        assertEquals(phoneNote2, phoneNotes.get(1));
    }

    @Test
    public void testReadBySubStrInSecondName(){
        createObj();
        List<PhoneNote> phoneNotes = phoneNoteDAO.readBySubStrInSecondName("testS",client.getClientID());
        deleteObj();
        assertEquals(phoneNote, phoneNotes.get(0));
        assertEquals(phoneNote2, phoneNotes.get(1));
    }

    @Test
    public void testReadBySubStrInMobileNumber(){
        createObj();
        List<PhoneNote> phoneNotes = phoneNoteDAO.readBySubStrInMobileNumber("Mob",client.getClientID());
        deleteObj();
        assertEquals(phoneNote, phoneNotes.get(0));
        assertEquals(phoneNote2, phoneNotes.get(1));
    }

    @Test
    public void testCreateAndRead() {
        createObj();
        PhoneNote phoneNote3 = new PhoneNote();
        phoneNote3.setNoteOwner(client);
        phoneNote3.setFirstName("testFName3");
        phoneNote3.setSecondName("testSName3");
        phoneNote3.setAdditionalName("testAName3");
        phoneNote3.setMobileNumber("testMobNum3");
        phoneNote3.setHomeNumber("testHNum3");
        phoneNote3.setLocation("testLocation3");
        phoneNote3.setEmail("testEmail3");
        phoneNoteDAO.create(phoneNote3);
        PhoneNote phoneNote1 = phoneNoteDAO.read(phoneNote3.getNoteID());

        phoneNoteDAO.delete(phoneNote3.getNoteID());
        deleteObj();
        assertEquals(phoneNote1, phoneNote3);
    }

    @Test
    public void testReadAll(){
        createObj();
        List<PhoneNote> phoneNotes = phoneNoteDAO.readAll();
        deleteObj();
        assertEquals(phoneNote, phoneNotes.get(0));
        assertEquals(phoneNote2, phoneNotes.get(1));
    }

    @Test
    public void testUpdate(){
        createObj();
        PhoneNote phoneNote4 = new PhoneNote();
        phoneNote4.setNoteOwner(client);
        phoneNote4.setFirstName("testFName4");
        phoneNote4.setSecondName("testSName4");
        phoneNote4.setAdditionalName("testAName4");
        phoneNote4.setMobileNumber("testMobNum4");
        phoneNote4.setHomeNumber("testHNum4");
        phoneNote4.setLocation("testLocation4");
        phoneNote4.setEmail("testEmail4");

        phoneNoteDAO.create(phoneNote4);

        phoneNote4.setFirstName("testNewFName4");
        phoneNote4.setSecondName("testNewSName4");
        phoneNote4.setAdditionalName("testNewAName4");
        phoneNote4.setMobileNumber("testNewMobNum4");
        phoneNote4.setHomeNumber("testNewHNum4");
        phoneNote4.setLocation("testNewLocation4");
        phoneNote4.setEmail("testNewEmail4");

        phoneNoteDAO.update(phoneNote4);
        PhoneNote phoneNote1 = phoneNoteDAO.read(phoneNote4.getNoteID());
        phoneNoteDAO.delete(phoneNote4.getNoteID());
        deleteObj();

        assertEquals(phoneNote4,phoneNote1);
    }

    @Test
    public void testDelete(){
        createObj();
        PhoneNote phoneNote4 = new PhoneNote();
        PhoneNote phoneNote5;
        PhoneNote phoneNote6 = new PhoneNote();
        phoneNote4.setNoteOwner(client);
        phoneNote4.setFirstName("testFName4");
        phoneNote4.setSecondName("testSName4");
        phoneNote4.setAdditionalName("testAName4");
        phoneNote4.setMobileNumber("testMobNum4");
        phoneNote4.setHomeNumber("testHNum4");
        phoneNote4.setLocation("testLocation4");
        phoneNote4.setEmail("testEmail4");

        phoneNoteDAO.create(phoneNote4);
        phoneNoteDAO.delete(phoneNote4.getNoteID());

        try{
            phoneNote5 = phoneNoteDAO.read(phoneNote4.getNoteID());
        }catch (EmptyResultDataAccessException erdae){
            phoneNote5 = new PhoneNote();
        }
        deleteObj();
        assertEquals(phoneNote5,phoneNote6);
    }
}