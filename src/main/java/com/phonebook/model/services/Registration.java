package com.phonebook.model.services;

import com.phonebook.entities.Client;
import com.phonebook.model.dao.ClientDAO;
import com.phonebook.model.dao.impl.JDBCClient;
import com.phonebook.model.exceptions.DuplicateClientDataException;

import java.util.List;

/**
 * Created by Руслан on 08.03.2017.
 */
public class Registration {
    private ClientDAO clientDAO = new JDBCClient();
    private List<Client> clientList = clientDAO.readAll();

    public void createNewUser(Client client) throws DuplicateClientDataException {

        for (Client clientFromList : clientList) {
            if ((clientFromList.getClientLogin().equals(client.getClientLogin())) || (clientFromList.getClientFullName().equals(client.getClientFullName()))) {
                throw new DuplicateClientDataException();
            }
        }

        clientDAO.create(client);
    }
}
