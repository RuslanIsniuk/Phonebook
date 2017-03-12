package com.phonebook.controller.command.impl;

import com.phonebook.controller.command.Command;
import com.phonebook.model.exceptions.ServiceException;
import com.phonebook.model.services.ClientOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand extends Command {
    private static final Logger logger = Logger.getLogger(RegistrationCommand.class);
    private static final String DEFAULT_OPERATION_FAIL_PATH = "/jsp/registration.jsp";
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/index.jsp";
    @Autowired
    private ClientOperations clientOperations;

    @Override
    public String execute(HttpServletRequest request) {
        String pathToJSP;
        String clientLogin = request.getParameter("username");
        String clientPass = request.getParameter("password");
        String clientFullName = request.getParameter("clientFullName");

        try {
            clientOperations.registration(clientLogin, clientPass, clientFullName);
            pathToJSP = DEFAULT_OPERATION_CONFIRM_PATH;
        } catch (ServiceException se) {
            logger.error(se);
            request.setAttribute("errorMessage", se.getMessage());
            pathToJSP = DEFAULT_OPERATION_FAIL_PATH;
        }
        return pathToJSP;
    }
}
