package com.phonebook.model.dao.impl;

import com.phonebook.entities.Client;
import com.phonebook.entities.ClientList;
import com.phonebook.model.dao.ClientDAO;
import com.phonebook.parser.Parser;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;

public class XMLClientDAO implements ClientDAO {
    private static final Logger logger = Logger.getLogger(XMLClientDAO.class);
    private ClientList clientList;
    private Parser parser;
    private File clientFile;

    public XMLClientDAO(Parser parser,File file) {
        this.parser = parser;
        this.clientFile = file;

        try {
            clientList = (ClientList) parser.getFromFile(clientFile, ClientList.class);
        } catch (JAXBException je) {
            logger.error(je);
        }

    }

    @Override
    public Client read(int clientID) {
        Client client = new Client();
        for (Client clientFromList : clientList.getClientList()) {
            if (clientID == clientFromList.getClientID()) {
                client = clientFromList;
            }
        }
        return client;
    }

    @Override
    public Client readByLogin(String clientLogin) {
        Client client = new Client();
        for (Client clientFromList : clientList.getClientList()) {
            if (clientLogin.equals(clientFromList.getClientLogin())) {
                client = clientFromList;
            }
        }
        return client;
    }

    @Override
    public List<Client> readAll() {
        return clientList.getClientList();
    }

    @Override
    public void create(Client client) {
        boolean iterate = true;
        int clientID = 1;

        while (iterate) {
            boolean clientInkr = false;

            for (Client clientFromList : clientList.getClientList()) {
                if (clientID == clientFromList.getClientID()) {
                    clientID++;
                    clientInkr = true;
                }
            }

            if (!clientInkr) {
                iterate = false;
            }
        }
        client.setClientID(clientID);
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
        for (Client clientFromList : clientList.getClientList()) {
            if (clientID == clientFromList.getClientID()) {
                clientList.removeClient(clientFromList);
            }
        }
    }
}
