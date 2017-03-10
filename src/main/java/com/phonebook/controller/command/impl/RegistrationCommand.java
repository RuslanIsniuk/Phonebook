package com.phonebook.controller.command.impl;

import com.phonebook.controller.command.Command;
import com.phonebook.model.exceptions.ServiceException;
import com.phonebook.model.services.ClientOperations;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Руслан on 10.03.2017.
 */
public class RegistrationCommand implements Command {
    private ClientOperations clientOperations = new ClientOperations();

    @Override
    public String execute(HttpServletRequest request) {
        String pathToJSP = "";
        String clientLogin = request.getParameter("username");
        String clientPass = request.getParameter("password");
        String clientFullName = request.getParameter("clientFullName");

        try {
            clientOperations.registration(clientLogin, clientPass, clientFullName);
        } catch (ServiceException se) {
            request.setAttribute("errorMessage", se.getMessage());
            pathToJSP = "/jsp/registration.jsp";
        }
        return pathToJSP;
    }
}
