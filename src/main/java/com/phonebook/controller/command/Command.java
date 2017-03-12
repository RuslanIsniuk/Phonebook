package com.phonebook.controller.command;

import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.model.services.ClientOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.http.HttpServletRequest;


public abstract class Command {
    private static final Logger logger = Logger.getLogger(Command.class);
    @Autowired
    private ClientOperations clientOperations;

    public abstract String execute(HttpServletRequest request) throws AuthorizationException;

    public Client getClientFromSession(HttpServletRequest request) throws AuthorizationException {
        try {
            return (Client) request.getSession().getAttribute("clientData");
        } catch (NullPointerException npe) {
            logger.error(npe);
            throw new AuthorizationException("ERROR! Session not valid!");
        }
    }

    public int getNoteIDFromRequest(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("noteID"));
    }

    public void passClientValidation(HttpServletRequest request) throws AuthorizationException {
        int clientID = Integer.parseInt(request.getParameter("clientID"));
        Client client = (Client) request.getSession().getAttribute("clientData");

        if (!clientOperations.checkClientID(client, clientID)) {
            throw new AuthorizationException();
        }
    }
}
