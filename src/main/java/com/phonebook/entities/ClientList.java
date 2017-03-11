package com.phonebook.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "clients")
public class ClientList {
    private List<Client> clients;

    public List<Client> getClientList() {
        return clients;
    }

    @XmlElement(name = "client")
    public void setClientList(List<Client> clientList) {
        this.clients = clientList;
    }
}
