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
        } catch (IOException ioe) {
            logger.error(ioe);
        } catch (JAXBException je) {
            logger.error(je);
        }

        for (Client clientFromList : clientList.getClientList()) {
            if (clientFromList.getClientID() > clientIDCounter) {
                clientIDCounter = clientFromList.getClientID();
            }
        }
    }

    @Override
    public Client read(int clientID) {
        try {
            for (Client clientFromList : clientList.getClientList()) {
                if (clientID == clientFromList.getClientID()) {
                    return clientFromList;
                }
            }
            throw new NullPointerException();
        } catch (NullPointerException ne) {
            logger.error(ne);
        }
        return null;
    }

    @Override
    public Client readByLogin(String clientLogin) {
        try {
            for (Client clientFromList : clientList.getClientList()) {
                if (clientLogin.equals(clientFromList.getClientLogin())) {
                    return clientFromList;
                }
            }
            throw new NullPointerException();
        } catch (NullPointerException ne) {
            logger.error(ne);
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
        try {
            boolean operationStatus = false;
            for (Client clientFromList : clientList.getClientList()) {
                if (client.getClientID() == clientFromList.getClientID()) {
                    clientList.removeClient(clientFromList);
                    clientList.addClient(client);
                    operationStatus = true;
                }
            }
            if (!operationStatus) {
                throw new NullPointerException();
            }
        } catch (NullPointerException ne) {
            logger.error(ne);
        }
    }

    @Override
    public void delete(int clientID) {
        try {
            boolean operationStatus = false;
            for (Client clientFromList : clientList.getClientList()) {
                if (clientID == clientFromList.getClientID()) {
                    clientList.removeClient(clientFromList);
                    operationStatus = true;
                }
            }
            if (!operationStatus) {
                throw new NullPointerException();
            }
        } catch (NullPointerException ne) {
            logger.error(ne);
        }


    }
}
