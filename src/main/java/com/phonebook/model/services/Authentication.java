package com.phonebook.model.services;

import com.phonebook.entities.Client;
import com.phonebook.model.dao.ClientDAO;
import com.phonebook.model.dao.impl.JDBCClient;
import com.phonebook.model.exceptions.LoginOrPassIncorrectException;

/**
 * Created by Руслан on 06.03.2017.
 */
public class Authentication {
    private ClientDAO clientDAO = new JDBCClient();

    public Client checkAuthentication(String clientLogin, String clientPass) throws LoginOrPassIncorrectException {
        Client client;

        try {
            client = clientDAO.read(clientLogin, clientPass);
        } catch (NullPointerException ne) {
            throw new LoginOrPassIncorrectException();
        }

        return client;
    }
}
