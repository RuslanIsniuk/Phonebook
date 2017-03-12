package com.phonebook.controller.command.impl;

import com.phonebook.controller.command.Command;
import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.model.exceptions.ServiceException;
import com.phonebook.model.services.ClientOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthenticationCommand extends Command {
    private static final Logger logger = Logger.getLogger(AuthenticationCommand.class);
    private static final String DEFAULT_OPERATION_FAIL_PATH = "/index.jsp";
    @Autowired
    private ClientOperations clientOperations;
    @Autowired
    private OpenPageCommand openPageCommand;

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
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
            logger.error(se);
            request.setAttribute("errorMessage", se.getMessage());
            pathToJSP = DEFAULT_OPERATION_FAIL_PATH;
        }
        return pathToJSP;
    }


}
