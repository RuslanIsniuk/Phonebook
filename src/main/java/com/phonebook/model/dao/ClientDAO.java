package com.phonebook.model.dao;

import com.phonebook.entities.Client;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Руслан on 06.03.2017.
 */
public interface ClientDAO {
    Client read(int clientID);

    Client readByLogin(String clientLogin);

    List<Client> readAll();

    void create(Client client);

    void update(Client client);

    void delete(int clientID);
}
