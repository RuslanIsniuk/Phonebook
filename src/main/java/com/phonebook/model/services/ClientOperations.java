package com.phonebook.model.services;

import com.phonebook.entities.Client;
import com.phonebook.model.dao.ClientDAO;
import com.phonebook.model.dao.impl.JDBCClient;
import com.phonebook.model.exceptions.DuplicateClientDataException;
import com.phonebook.model.exceptions.LoginOrPassIncorrectException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class ClientOperations {
    private ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
    private ClientDAO clientDAO = (JDBCClient)ac.getBean("clientDAO");
    private List<Client> clientList = clientDAO.readAll();

    public Client Authentication(String clientLogin) throws LoginOrPassIncorrectException {
        Client client;

        try {
            client = clientDAO.readByLogin(clientLogin);
        } catch (NullPointerException ne) {
            throw new LoginOrPassIncorrectException();
        }

        return client;
    }

    public void Registration(Client client) throws DuplicateClientDataException {

        for (Client clientFromList : clientList) {
            if ((clientFromList.getClientLogin().equals(client.getClientLogin())) || (clientFromList.getClientFullName().equals(client.getClientFullName()))) {
                throw new DuplicateClientDataException();
            }
        }

        clientDAO.create(client);
    }

}
