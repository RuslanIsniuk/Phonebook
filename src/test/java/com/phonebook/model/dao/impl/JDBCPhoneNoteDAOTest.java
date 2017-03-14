package com.phonebook.model.dao.impl;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.phonebook.entities.Client;
import com.phonebook.entities.PhoneNote;
import com.phonebook.model.dao.ClientDAO;
import com.phonebook.model.dao.PhoneNoteDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-dao.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class JDBCPhoneNoteDAOTest {
    @Autowired
    private  ClientDAO clientDAO;
    @Autowired
    private  PhoneNoteDAO phoneNoteDAO;
    private Client client;
    private PhoneNote phoneNote;
    private PhoneNote phoneNote2;

    @Before
    public void setUp() throws Exception {
        client = new Client();
        phoneNote = new PhoneNote();
        phoneNote2 = new PhoneNote();

        client.setClientID(1);
        client.setClientLogin("testLogin");
        client.setClientPass("testPass");
        client.setClientFullName("testFullName");

        phoneNote.setNoteID(1);
        phoneNote.setFirstName("testFName");
        phoneNote.setSecondName("testSName");
        phoneNote.setAdditionalName("testAName");
        phoneNote.setMobileNumber("testMobNum");
        phoneNote.setHomeNumber("testHNum");
        phoneNote.setLocation("testLocation");
        phoneNote.setEmail("testEmail");

        phoneNote2.setNoteID(2);
        phoneNote2.setFirstName("testFName2");
        phoneNote2.setSecondName("testSName2");
        phoneNote2.setAdditionalName("testAName2");
        phoneNote2.setMobileNumber("testMobNum2");
        phoneNote2.setHomeNumber("testHNum2");
        phoneNote2.setLocation("testLocation2");
        phoneNote2.setEmail("testEmail2");

        phoneNote.setNoteOwner(client);
        phoneNote2.setNoteOwner(client);

    }

    @After
    public void tearDown(){
        client = null;
        phoneNote = null;
        phoneNote2 = null;
    }

    @Test
    @DatabaseSetup("/ds/1phoneNote-ds.xml")
    public void read(){
        PhoneNote phoneNoteActual = phoneNoteDAO.read(1);
        assertEquals(phoneNote, phoneNoteActual);
    }

    @Test
    @DatabaseSetup("/ds/1phoneNote-ds.xml")
    public void readByName() {
        PhoneNote phoneNote1 = phoneNoteDAO.readByName(phoneNote.getFirstName(), phoneNote.getSecondName());
        assertEquals(phoneNote, phoneNote1);
    }

    @Test
    @DatabaseSetup("/ds/2phoneNote-ds.xml")
    public void readBySubStrInFirstName() {
        List<PhoneNote> phoneNoteListExpected = Arrays.asList(phoneNote,phoneNote2);
        List<PhoneNote> phoneNotes = phoneNoteDAO.readBySubStrInFirstName("testF",client.getClientID());
        assertEquals(phoneNoteListExpected,phoneNotes);

    }

    @Test
    @DatabaseSetup("/ds/2phoneNote-ds.xml")
    public void readBySubStrInSecondName(){
        List<PhoneNote> phoneNoteListExpected = Arrays.asList(phoneNote,phoneNote2);
        List<PhoneNote> phoneNotes = phoneNoteDAO.readBySubStrInSecondName("testS",client.getClientID());
        assertEquals(phoneNoteListExpected,phoneNotes);
    }

    @Test
    @DatabaseSetup("/ds/2phoneNote-ds.xml")
    public void readBySubStrInMobileNumber(){
        List<PhoneNote> phoneNoteListExpected = Arrays.asList(phoneNote,phoneNote2);
        List<PhoneNote> phoneNotes = phoneNoteDAO.readBySubStrInMobileNumber("Mob",client.getClientID());
        assertEquals(phoneNoteListExpected,phoneNotes);
    }

    @Test
    @DatabaseSetup("/ds/blank-ds.xml")
    public void readNotExist(){
        PhoneNote phoneNote1 = phoneNoteDAO.read(phoneNote.getNoteID());
        assertNull(phoneNote1);
    }

    @Test
    @DatabaseSetup("/ds/blank-ds.xml")
    public void readByNameNotExist() {
        PhoneNote phoneNote1 = phoneNoteDAO.readByName(phoneNote.getFirstName(), phoneNote.getSecondName());
        assertNull(phoneNote1);
    }

    @Test
    @DatabaseSetup("/ds/blank-ds.xml")
    public void readBySubStrInFirstNameNotExist() {
        List<PhoneNote> phoneNotes = phoneNoteDAO.readBySubStrInFirstName("testF",1);
        List<PhoneNote> phoneNotes1 = new ArrayList<>();
        assertEquals(phoneNotes, phoneNotes1);
    }

    @Test
    @DatabaseSetup("/ds/blank-ds.xml")
    public void readBySubStrInSecondNameNotExist(){
        List<PhoneNote> phoneNotes = phoneNoteDAO.readBySubStrInSecondName("testS",1);
        List<PhoneNote> phoneNotes1 = new ArrayList<>();
        assertEquals(phoneNotes, phoneNotes1);
    }

    @Test
    @DatabaseSetup("/ds/blank-ds.xml")
    public void readBySubStrInMobileNumberNotExist(){
        List<PhoneNote> phoneNotes = phoneNoteDAO.readBySubStrInMobileNumber("Mob",1);
        List<PhoneNote> phoneNotes1 = new ArrayList<>();
        assertEquals(phoneNotes, phoneNotes1);
    }

    @Test
    @DatabaseSetup("/ds/blank-ds.xml")
    @ExpectedDatabase(
            assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/ds/expected-after-insert-1note-ds.xml")
    public void create() {
        clientDAO.insert(client);
        phoneNoteDAO.insert(phoneNote);
    }

    @Test
    @DatabaseSetup("/ds/2phoneNote-ds.xml")
    public void testReadAll(){
        List<PhoneNote> phoneNotes = phoneNoteDAO.readAll();
        assertEquals(phoneNote, phoneNotes.get(0));
        assertEquals(phoneNote2, phoneNotes.get(1));
    }

    @Test
    @DatabaseSetup("/ds/1phoneNote-ds.xml")
    @ExpectedDatabase(
            assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/ds/expectet-after-update-1note-update-ds.xml")
    public void testUpdate(){
        phoneNote.setFirstName("testFName4");
        phoneNote.setSecondName("testSName4");
        phoneNote.setAdditionalName("testAName4");
        phoneNote.setMobileNumber("testMobNum4");
        phoneNote.setHomeNumber("testHNum4");
        phoneNote.setLocation("testLocation4");
        phoneNote.setEmail("testEmail4");

        phoneNoteDAO.update(phoneNote);
        PhoneNote phoneNoteActual = phoneNoteDAO.read(1);

        assertEquals(phoneNote,phoneNoteActual);
    }

    @Test
    @DatabaseSetup("/ds/2phoneNote-ds.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/ds/1client-ds.xml")
    public void delete() {
        phoneNoteDAO.delete(1);
        phoneNoteDAO.delete(2);
    }
}