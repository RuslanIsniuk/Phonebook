package com.phonebook.model.dao.impl;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.phonebook.entities.Client;
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
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-dao.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class JDBCClientDAOTest {
    @Autowired
    private JDBCClientDAO jdbcClientDAO;
    private  Client client;

    @Before
    public void setClientData() throws Exception {
        client = new Client();
        client.setClientLogin("testLogin");
        client.setClientPass("testPass");
        client.setClientFullName("testFullName");
        client.setClientID(1);
    }

    @After
    public void deleteClient() throws Exception {
        client = null;
    }
    @Test
    @DatabaseSetup("/ds/1client-ds.xml")
    public void read() {
        Client clientActual = jdbcClientDAO.read(1);
        assertEquals(clientActual, client);
    }

    @Test
    @DatabaseSetup("/ds/1client-ds.xml")
    public void readNotExist() {
        Client clientActual = jdbcClientDAO.read(2);
        assertNull(clientActual);
    }



    @Test
    @DatabaseSetup("/ds/blank-ds.xml")
    @ExpectedDatabase(
            assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/ds/expected-after-insert-1client-ds.xml")
    public void testCreate() {
        jdbcClientDAO.insert(client);
    }

    @Test
    @DatabaseSetup("/ds/1client-ds.xml")
    public void readByLogin() {
        Client clientFromDB = jdbcClientDAO.readByLogin(client.getClientLogin());
        assertEquals(client, clientFromDB);
    }

    @Test
    @DatabaseSetup("/ds/blank-ds.xml")
    public void readByLoginNotExist() {
        Client clientActual = jdbcClientDAO.readByLogin("test2Login");
        assertNull(clientActual);
    }

    @Test
    @DatabaseSetup("/ds/blank-ds.xml")
    public void readAll(){
        List<Client> clientListExpected = new ArrayList<>();
        Client client2 = new Client();
        client2.setClientLogin("testLogin2");
        client2.setClientPass("testPass2");
        client2.setClientFullName("testFullName2");
        client2.setClientID(2);
        clientListExpected.add(client);
        clientListExpected.add(client2);

        jdbcClientDAO.insert(client);
        jdbcClientDAO.insert(client2);

        List<Client> clientList = jdbcClientDAO.readAll();

        jdbcClientDAO.delete(client.getClientID());
        jdbcClientDAO.delete(client2.getClientID());

        assertEquals(clientListExpected,clientList);
    }

    @Test
    @DatabaseSetup("/ds/blank-ds.xml")
    public void readAllNotExist(){
        List<Client> clientList = jdbcClientDAO.readAll();
        List<Client> clientListExpected = new ArrayList<>();

        assertEquals(clientListExpected,clientList);
    }

    @Test
    @DatabaseSetup("/ds/blank-ds.xml")
    public void update(){
        jdbcClientDAO.insert(client);
        client.setClientLogin("testLogin2");
        client.setClientPass("testPass2");
        client.setClientFullName("testFullName2");

        jdbcClientDAO.update(client);
        Client clientFromDB = jdbcClientDAO.read(client.getClientID());
        jdbcClientDAO.delete(client.getClientID());

        assertEquals(client,clientFromDB);

    }

    @Test
    @DatabaseSetup("/ds/1client-ds.xml")
    @ExpectedDatabase("/ds/blank-ds.xml")
    public void delete() {
        jdbcClientDAO.delete(1);
    }
}