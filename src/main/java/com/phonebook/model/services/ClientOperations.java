package com.phonebook.model.services;

import com.phonebook.entities.Client;
import com.phonebook.model.dao.ClientDAO;
import com.phonebook.model.exceptions.DuplicateClientDataException;
import com.phonebook.model.exceptions.ClientDataIncorrectException;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientOperations {
    @Autowired
    private ClientDAO clientDAO;

    private static final String LOGIN_FIELD = "[a-zA-Z]{3,45}";
    private static final String PASS_FIELD = "[^\\s]{5,45}";
    private static final String FULL_NAME_FIELD = "(\\w{5,45})||(\\w{5,20}\\s{1,5}\\w{5,20})||(\\w{5,12}\\s{1,2}\\w{5,12}\\s{1,2}\\w{5,12})";

    public boolean checkClientID(Client clientFromSession, int clientID){
        boolean checkPass;
        Client client = clientDAO.read(clientID);

        if((client.getClientLogin().equals(clientFromSession.getClientLogin()))&&(client.getClientFullName().equals(clientFromSession.getClientFullName()))){
            checkPass = true;
        }else{
            checkPass = false;
        }
        return checkPass;
    }

    public Client authentication(String clientLogin, String clientPass) throws ClientDataIncorrectException {
        validationOnCorrectInput(clientLogin,clientPass);
        Client client = clientDAO.readByLogin(clientLogin);

        if (!clientPass.equals(client.getClientPass())) {
            throw new ClientDataIncorrectException();
        }

        return client;
    }

    public void registration(String clientLogin, String clientPass, String clientFullName) throws DuplicateClientDataException,ClientDataIncorrectException {
        validationOnCorrectInput(clientLogin,clientPass,clientFullName);
        List<Client> clientList = clientDAO.readAll();

        for (Client clientFromList : clientList) {
            if ((clientFromList.getClientLogin().equals(clientLogin)) || (clientFromList.getClientFullName().equals(clientFullName))) {
                throw new DuplicateClientDataException();
            }
        }
        Client client = new Client();
        client.setClientLogin(clientLogin);
        client.setClientPass(clientPass);
        client.setClientFullName(clientFullName);
        clientDAO.create(client);
    }

    private boolean compileStr(String checkedObject, String compileStr) {
        Pattern pattern = Pattern.compile(compileStr);
        Matcher matcher = pattern.matcher(checkedObject);
        return matcher.matches();
    }

    private void validationOnCorrectInput(String clientLogin, String clientPass) throws ClientDataIncorrectException {
        if ((!compileStr(clientLogin, LOGIN_FIELD)) || (!compileStr(clientPass, PASS_FIELD))) {
            throw new ClientDataIncorrectException();
        }
    }

    private void validationOnCorrectInput(String clientLogin, String clientPass, String clientFullName) throws ClientDataIncorrectException {
        validationOnCorrectInput(clientLogin,clientPass);
        if(!compileStr(clientFullName,FULL_NAME_FIELD)){
            throw new ClientDataIncorrectException("ERROR! Such name is already exist!");
        }
    }

}
