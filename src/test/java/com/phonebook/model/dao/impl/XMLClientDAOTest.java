package com.phonebook.model.dao.impl;

import com.phonebook.entities.Client;
import com.phonebook.parser.Parser;
import com.phonebook.parser.impl.JaxbParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class XMLClientDAOTest {
    XMLClientDAO xmlClientDAO;
    Client client;
    File clientTestFile;
    Parser parser;

    @Before
    public void setUp(){
        clientTestFile = new File("testClientList.xml");
        parser = new JaxbParser();
        try {
            clientTestFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        client = new Client();
        client.setClientLogin("testLogin");
        client.setClientPass("testPass");
        client.setClientFullName("testFullName");
        client.setClientID(1);
        xmlClientDAO = new XMLClientDAO(parser,clientTestFile);
    }

    @After
    public void tearDown(){
        try{
            clientTestFile.delete();
        }catch (Exception e){
            e.printStackTrace();
        }

        client = null;
        xmlClientDAO = null;
    }

    @Test
    public void read() throws Exception {
        xmlClientDAO.insert(client);
        Client clientFromBD = xmlClientDAO.read(client.getClientID());
        xmlClientDAO.delete(client.getClientID());

        assertEquals(clientFromBD,client);
    }

    @Test
    public void readNotExist() throws Exception {
        Client clientFromBD = xmlClientDAO.read(client.getClientID());
        assertNull(clientFromBD);
    }

    @Test
    public void readByLogin() throws Exception {
        xmlClientDAO.insert(client);
        Client clientFromBD = xmlClientDAO.readByLogin(client.getClientLogin());
        xmlClientDAO.delete(client.getClientID());

        assertEquals(clientFromBD,client);
    }

    @Test
    public void readByLoginNotExist() throws Exception {
        Client clientFromBD = xmlClientDAO.readByLogin(client.getClientLogin());
        assertNull(clientFromBD);
    }

    @Test
    public void readAll() throws Exception {
        Client client2 = new Client();
        client2.setClientLogin("test2Login");
        client2.setClientPass("test2Pass");
        client2.setClientFullName("test2FullName");
        client2.setClientID(2);

        List<Client> clientList = new ArrayList<>();
        clientList.add(client);
        clientList.add(client2);

        xmlClientDAO.insert(client);
        xmlClientDAO.insert(client2);
        List<Client> clientListFromDB = xmlClientDAO.readAll();

        assertEquals(clientList,clientListFromDB);
    }

    @Test
    public void insert() throws Exception {
        xmlClientDAO.insert(client);
    }

    @Test
    public void update() throws Exception {
        xmlClientDAO.insert(client);
        client.setClientLogin("test2Login");
        client.setClientPass("test2Pass");
        client.setClientFullName("test2FullName");
        xmlClientDAO.update(client);
        Client clientFromDB = xmlClientDAO.read(client.getClientID());

        assertEquals(client,clientFromDB);
    }

    @Test
    public void delete() throws Exception {
        xmlClientDAO.insert(client);
        xmlClientDAO.delete(client.getClientID());
        Client clientFromBD = xmlClientDAO.read(client.getClientID());

        assertNull(clientFromBD);;
    }


}