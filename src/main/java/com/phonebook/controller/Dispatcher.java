package com.phonebook.controller;


import com.phonebook.controller.command.impl.AuthenticationCommand;
import com.phonebook.controller.command.impl.RegistrationCommand;

import javax.servlet.http.HttpServletRequest;

public class Dispatcher {
    private static final Dispatcher Instance = new Dispatcher();

    private AuthenticationCommand authenticationCommand = new AuthenticationCommand();
    private RegistrationCommand registrationCommand = new RegistrationCommand();

    private Dispatcher() {
    }

    public String logicIdentificator(HttpServletRequest request) {
        String pathToJSP = new String();
        String attributeStr = request.getParameter("actionType");


        switch (attributeStr) {
            case "Authentication":
                pathToJSP = authenticationCommand.execute(request);
                break;

            case "Registration":
                pathToJSP = registrationCommand.execute(request);
                break;
        }
        return pathToJSP;

    }

    public static Dispatcher getInstance() {
        return Instance;
    }
}