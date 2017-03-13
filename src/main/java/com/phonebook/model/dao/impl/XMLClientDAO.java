package com.phonebook.model.dao.impl;

import com.phonebook.entities.Client;
import com.phonebook.model.dao.ClientList;
import com.phonebook.model.dao.ClientDAO;
import com.phonebook.parser.Parser;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLClientDAO implements ClientDAO {
    private static final Logger logger = Logger.getLogger(XMLClientDAO.class);
    private ClientList clientList;
    private Parser parser;
    private File clientFile;
    private int clientIDCounter = 0;

    public XMLClientDAO(Parser parser, File file) {
        this.parser = parser;
        this.clientFile = file;

        try {
            if (!clientFile.exists()) {
                clientFile.createNewFile();
            }
            clientList = (ClientList) parser.getFromFile(clientFile, ClientList.class);
        } catch (IOException | JAXBException | NullPointerException e) {
            logger.error(e);
            clientList = new ClientList();
        }

        for (Client clientFromList : clientList.getClientList()) {
            if (clientFromList.getClientID() > clientIDCounter) {
                clientIDCounter = clientFromList.getClientID();
            }
        }
    }

    @Override
    public Client read(int clientID) {
        for (Client clientFromList : clientList.getClientList()) {
            if (clientID == clientFromList.getClientID()) {
                return clientFromList;
            }
        }
        return null;
    }

    @Override
    public Client readByLogin(String clientLogin) {
        for (Client clientFromList : clientList.getClientList()) {
            if (clientLogin.equals(clientFromList.getClientLogin())) {
                return clientFromList;
            }
        }
        return null;
    }

    @Override
    public List<Client> readAll() {
        return clientList.getClientList();
    }

    @Override
    public void insert(Client client) {
        client.setClientID(++clientIDCounter);
        clientList.addClient(client);
    }

    @Override
    public void update(Client client) {
        for (Client clientFromList : clientList.getClientList()) {
            if (client.getClientID() == clientFromList.getClientID()) {
                clientList.removeClient(clientFromList);
                clientList.addClient(client);
            }
        }
    }

    @Override
    public void delete(int clientID) {
        Client client = new Client();
        for (Client clientFromList : clientList.getClientList()) {
            if (clientID == clientFromList.getClientID()) {
                client = clientFromList;
            }
        }
        clientList.removeClient(client);
    }
}
