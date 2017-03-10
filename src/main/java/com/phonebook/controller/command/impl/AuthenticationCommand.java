package com.phonebook.controller.command.impl;

import com.phonebook.controller.command.Command;
import com.phonebook.entities.Client;
import com.phonebook.model.exceptions.ServiceException;
import com.phonebook.model.services.ClientOperations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthenticationCommand implements Command {
    private ClientOperations clientOperations = new ClientOperations();

    @Override
    public String execute (HttpServletRequest request) {
        String clientLogin = request.getParameter("username");
        String clientPass = request.getParameter("password");
        String pathToJSP;

        try {
            Client client = clientOperations.authentication(clientLogin, clientPass);
            request.setAttribute("clientID", client.getClientID());

            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(600);
            session.setAttribute("clientData", client);

            pathToJSP = "/jsp/mainPage.jsp";
        } catch (ServiceException se) {
            request.setAttribute("errorMessage", se.getMessage());
            pathToJSP = "/index.jsp";
        }
        return pathToJSP;
    }


}
