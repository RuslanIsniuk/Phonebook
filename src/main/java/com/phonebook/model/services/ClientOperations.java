package com.phonebook.model.services;

import com.phonebook.entities.Client;
import com.phonebook.model.dao.ClientDAO;
import com.phonebook.model.exceptions.DuplicateClientDataException;
import com.phonebook.model.exceptions.LoginOrPassIncorrectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class ClientOperations {
    @Autowired
    private ClientDAO clientDAO;

    private static final String LOGIN_FIELD = "[a-zA-Z]{3,45}";
    private static final String PASS_FIELD = "[^\\s]{5,45}";
    private static final String FULL_NAME_FIELD = "(\\w{5,20}\\s{1,5}\\w{5,20})||(\\w{5,12}\\s{1,2}\\w{5,12}\\s{1,2}\\w{5,12})";

    public Client authentication(String clientLogin, String clientPass) throws LoginOrPassIncorrectException {

        if((!validationOnCorrectInput(clientLogin,LOGIN_FIELD))||(!validationOnCorrectInput(clientPass,PASS_FIELD))){
            throw new LoginOrPassIncorrectException();
        }

        Client client = clientDAO.readByLogin(clientLogin);

        if(!clientPass.equals(client.getClientPass())){
            throw new LoginOrPassIncorrectException();
        }

        return client;
    }

    public void registration(Client client) throws DuplicateClientDataException {
        List<Client> clientList = clientDAO.readAll();
        for (Client clientFromList : clientList) {
            if ((clientFromList.getClientLogin().equals(client.getClientLogin())) || (clientFromList.getClientFullName().equals(client.getClientFullName()))) {
                throw new DuplicateClientDataException();
            }
        }

        clientDAO.create(client);
    }

    private boolean validationOnCorrectInput(String checkedObject, String compileStr) {
        Pattern pattern = Pattern.compile(compileStr);
        Matcher matcher = pattern.matcher(checkedObject);
        return matcher.matches();
    }

}
