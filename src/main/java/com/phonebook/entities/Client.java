package com.phonebook.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "client")
public class Client {
    private int clientID;
    private String clientLogin;
    private String clientPass;
    private String clientFullName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (clientID != client.clientID) return false;
        if (clientLogin != null ? !clientLogin.equals(client.clientLogin) : client.clientLogin != null) return false;
        if (clientPass != null ? !clientPass.equals(client.clientPass) : client.clientPass != null) return false;
        return clientFullName != null ? clientFullName.equals(client.clientFullName) : client.clientFullName == null;
    }

    @Override
    public int hashCode() {
        int result = clientID;
        result = 31 * result + (clientLogin != null ? clientLogin.hashCode() : 0);
        result = 31 * result + (clientPass != null ? clientPass.hashCode() : 0);
        result = 31 * result + (clientFullName != null ? clientFullName.hashCode() : 0);
        return result;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public String getClientPass() {
        return clientPass;
    }

    public void setClientPass(String clientPass) {
        this.clientPass = clientPass;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }
}
