package com.phonebook.parser.impl;

import com.phonebook.entities.Client;
import com.phonebook.parser.Parser;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.File;


/**
 * Created by Руслан on 06.03.2017.
 */
public class ClientParserTest extends TestCase {
    private Parser parser;
    private File file;

    @Before
    public void setUp() throws Exception {
        parser = new ClientParser();
        file = new File("Client.xml");
    }

    @Test
    public void testGetObject() throws Exception {
        Client client = (Client) parser.getObject(file, Client.class);
        System.out.println(client.getClientFullName());
    }

    @Test
    public void testSaveObject() throws Exception {
        Client client = new Client();
        client.setClientLogin("testLogin");
        client.setClientPass("testPass");
        client.setClientFullName("testClientFullName");
        Client client1 = new Client();
        client1.setClientLogin("testLogin1");
        client1.setClientPass("testPass1");
        client1.setClientFullName("testClientFullName1");


        parser.saveObject(file,client);
        parser.saveObject(file,client1);
    }

}