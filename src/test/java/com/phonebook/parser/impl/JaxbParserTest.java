package com.phonebook.parser.impl;

import com.phonebook.entities.Client;
import com.phonebook.entities.PhoneNote;
import com.phonebook.model.dao.ClientList;
import com.phonebook.model.dao.PhoneNoteList;
import com.phonebook.parser.Parser;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JaxbParserTest {
    private Parser parser = new JaxbParser();
    private File clientFile = new File("clientList.xml");
    private File phoneNoteFile = new File("phoneNoteList.xml");

    @Before
    public void createFiles() throws Exception {

        try {
            clientFile.createNewFile();
            phoneNoteFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void delete() throws Exception {
        try {
            clientFile.delete();
            phoneNoteFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClientObject() throws Exception {
        Client client = new Client();
        client.setClientID(1);
        client.setClientLogin("testLogin");
        client.setClientPass("testPass");
        client.setClientFullName("testClientFullName");
        Client client1 = new Client();
        client1.setClientID(2);
        client1.setClientLogin("testLogin1");
        client1.setClientPass("testPass1");
        client1.setClientFullName("testClientFullName1");
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        clients.add(client1);

        ClientList clientList = new ClientList();
        clientList.setClientList(clients);

        parser.writeInFile(clientFile, clientList);
        ClientList clientList2 = (ClientList) parser.getFromFile(clientFile, ClientList.class);
        System.out.println(clientList2.getClientList());
    }

    @Test
    public void testGetPhoneNoteObject() throws Exception {
        Client client = new Client();
        client.setClientID(1);
        client.setClientLogin("testLogin");
        client.setClientPass("testPass");
        client.setClientFullName("testClientFullName");

        PhoneNote phoneNote = new PhoneNote();
        phoneNote.setNoteID(1);
        phoneNote.setFirstName("testFName");
        phoneNote.setSecondName("testSName");
        phoneNote.setAdditionalName("testAName");
        phoneNote.setMobileNumber("testMobNum");
        phoneNote.setHomeNumber("testHNum");
        phoneNote.setLocation("testLocation");
        phoneNote.setEmail("testEmail");
        phoneNote.setNoteOwner(client);

        PhoneNote phoneNote2 = new PhoneNote();
        phoneNote2.setNoteID(2);
        phoneNote2.setFirstName("testFName2");
        phoneNote2.setSecondName("testSName2");
        phoneNote2.setAdditionalName("testAName2");
        phoneNote2.setMobileNumber("testMobNum2");
        phoneNote2.setHomeNumber("testHNum2");
        phoneNote2.setLocation("testLocation2");
        phoneNote2.setEmail("testEmail2");
        phoneNote2.setNoteOwner(client);

        List<PhoneNote> phoneNotes = new ArrayList<>();
        phoneNotes.add(phoneNote);
        phoneNotes.add(phoneNote2);

        PhoneNoteList phoneNoteList = new PhoneNoteList();
        phoneNoteList.setNoteList(phoneNotes);

        parser.writeInFile(phoneNoteFile, phoneNoteList);
        PhoneNoteList phoneNoteList1 = (PhoneNoteList) parser.getFromFile(phoneNoteFile, PhoneNoteList.class);
        System.out.println(phoneNoteList1.getNoteList());
    }

}