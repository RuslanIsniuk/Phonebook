package com.phonebook.model.dao;

import com.phonebook.entities.Client;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Руслан on 06.03.2017.
 */
public interface ClientDAO {
    Client read(String clientLogin,String clientPass);
    List<Client> readAll();
    void create(Client client);
    void update(Client client);
    void delete(String clientLogin);
}
