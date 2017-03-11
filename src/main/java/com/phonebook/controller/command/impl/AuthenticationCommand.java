package com.phonebook.controller.command.impl;

import com.phonebook.controller.command.Command;
import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.model.exceptions.ServiceException;
import com.phonebook.model.services.ClientOperations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthenticationCommand extends Command {
    @Autowired
    private ClientOperations clientOperations;
    @Autowired
    private OpenPageCommand openPageCommand;

    @Override
    public String execute (HttpServletRequest request)throws AuthorizationException {
        String clientLogin = request.getParameter("username");
        String clientPass = request.getParameter("password");
        String pathToJSP;

        try {
            Client client = clientOperations.authentication(clientLogin, clientPass);

            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(600);
            session.setAttribute("clientData", client);

            pathToJSP = openPageCommand.execute(request);
        } catch (ServiceException se) {
            request.setAttribute("errorMessage", se.getMessage());
            pathToJSP = "/index.jsp";
        }
        return pathToJSP;
    }


}
