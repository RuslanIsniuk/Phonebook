package com.phonebook.controller;


import com.phonebook.controller.command.impl.*;
import com.phonebook.controller.exceptions.AuthorizationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class Dispatcher {
    private static final Logger logger = Logger.getLogger(Dispatcher.class);
    private static final Dispatcher Instance = new Dispatcher();
    @Autowired
    private AuthenticationCommand authenticationCommand;
    @Autowired
    private RegistrationCommand registrationCommand;
    @Autowired
    private OpenPageCommand openPageCommand;
    @Autowired
    private FilterByCommand filterByCommand;
    @Autowired
    private EditNoteCommand editNoteCommand;
    @Autowired
    private AddNoteCommand addNoteCommand;
    @Autowired
    private DeleteNoteCommand deleteNoteCommand;

    private Dispatcher() {
    }

    public String logicIdentificator(HttpServletRequest request) {
        String pathToJSP = "";
        String attributeStr = request.getParameter("actionType");

        try {
            switch (attributeStr) {
                case "openRegistrationPage":
                case "viewPhonebook":
                case "openEditNotePage":
                case "openDeleteNotePage":
                case "returnToMainPage":
                case "openAddNotePage":
                    pathToJSP = openPageCommand.execute(request);
                    break;

                case "authentication":
                    pathToJSP = authenticationCommand.execute(request);
                    break;

                case "registration":
                    pathToJSP = registrationCommand.execute(request);
                    break;

                case "filterByFirstName":
                case "filterBySecondName":
                case "filterByMobNum":
                    pathToJSP = filterByCommand.execute(request);
                    break;

                case "editNoteConfirm":
                    pathToJSP = editNoteCommand.execute(request);
                    break;

                case "addNoteConfirm":
                    pathToJSP = addNoteCommand.execute(request);
                    break;

                case "deleteNoteConfirm":
                    pathToJSP = deleteNoteCommand.execute(request);
                    break;

                case "LogOut":
                    request.getSession().invalidate();
                    pathToJSP = "/index.jsp";
                    break;

                default:
                    logger.error("Get default case!");
                    break;
            }
        } catch (AuthorizationException ae) {
            logger.error(ae);
            request.setAttribute("errorMessage", ae.getMessage());
            pathToJSP = "/index.jsp";
        }

        return pathToJSP;

    }

    public static Dispatcher getInstance() {
        return Instance;
    }
}
