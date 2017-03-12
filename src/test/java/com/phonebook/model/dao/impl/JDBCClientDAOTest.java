package com.phonebook.model.dao.impl;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.phonebook.entities.Client;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-dao.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class JDBCClientDAOTest {
    @Autowired
    private JDBCClientDAO jdbcClientDAO;
    private static Client client = new Client();

    @BeforeClass
    public static void setClientData() throws Exception {
        client.setClientLogin("testLogin");
        client.setClientPass("testPass");
        client.setClientFullName("testFullName");
    }

    @Test
    @DatabaseSetup("/ds/1client-ds.xml")
    public void testRead() {
        Client clientActual = jdbcClientDAO.read(1);
        assertEquals(clientActual, client);
    }

    @Test
    @ExpectedDatabase(
            assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/ds/expected-after-insert-1client-ds.xml")
    public void testCreate() {
        jdbcClientDAO.insert(client);
    }

    @Test
    @DatabaseSetup("/ds/blank-ds.xml")
    public void testReadByLogin() {
        jdbcClientDAO.insert(client);
        Client client1 = jdbcClientDAO.readByLogin(client.getClientLogin());
        jdbcClientDAO.delete(client.getClientID());

        assertEquals(client, client1);
    }

    @Test
    @DatabaseSetup("/ds/1client-ds.xml")
    @ExpectedDatabase("/ds/blank-ds.xml")
    public void testDelete() {
        jdbcClientDAO.delete(1);
    }
}