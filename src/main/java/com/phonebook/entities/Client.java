package com.phonebook.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Руслан on 06.03.2017.
 */
@XmlRootElement(name = "client")
@XmlType(propOrder = {"clientLogin","clientPass","clientFullName"})
public class Client {
    private String clientLogin;
    private String clientPass;
    private String clientFullName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (clientLogin != null ? !clientLogin.equals(client.clientLogin) : client.clientLogin != null) return false;
        if (clientPass != null ? !clientPass.equals(client.clientPass) : client.clientPass != null) return false;
        return clientFullName != null ? clientFullName.equals(client.clientFullName) : client.clientFullName == null;
    }

    @Override
    public int hashCode() {
        int result = clientLogin != null ? clientLogin.hashCode() : 0;
        result = 31 * result + (clientPass != null ? clientPass.hashCode() : 0);
        result = 31 * result + (clientFullName != null ? clientFullName.hashCode() : 0);
        return result;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    @XmlElement(name = "client_login")
    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public String getClientPass() {
        return clientPass;
    }

    @XmlElement(name = "client_pass")
    public void setClientPass(String clientPass) {
        this.clientPass = clientPass;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    @XmlElement(name = "client_full_name")
    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }
}
