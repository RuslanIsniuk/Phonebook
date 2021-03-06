package com.phonebook.model.dao;

import com.phonebook.entities.Client;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@XmlRootElement(name = "clients")
public class ClientList {
    private List<Client> clients = new ArrayList<>();

    public List<Client> getClientList() {
        return clients;
    }

    @XmlElement(name = "client")
    public void setClientList(List<Client> clientList) {
        this.clients = clientList;
    }

    public void addClient(Client client){
        clients.add(client);
    }

    public void removeClient(Client client){
        Iterator<Client> iter = clients.iterator();

        while (iter.hasNext()) {
           Client clientFromList = iter.next();

            if (client.getClientID() == clientFromList.getClientID())
                iter.remove();
        }
    }
}
