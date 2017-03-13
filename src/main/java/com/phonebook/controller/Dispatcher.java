package com.phonebook.controller;

import com.phonebook.controller.command.impl.*;
import com.phonebook.controller.exceptions.AuthorizationException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class Dispatcher {
    private static final Logger logger = Logger.getLogger(Dispatcher.class);
    private static final Dispatcher Instance = new Dispatcher();
    private AuthenticationCommand authenticationCommand;
    private RegistrationCommand registrationCommand;
    private OpenPageCommand openPageCommand;
    private FilterByCommand filterByCommand;
    private EditNoteCommand editNoteCommand;
    private AddNoteCommand addNoteCommand;
    private DeleteNoteCommand deleteNoteCommand;

    private Dispatcher() {
    }

    public Dispatcher(AuthenticationCommand authenticationCommand, RegistrationCommand registrationCommand,
                      OpenPageCommand openPageCommand, FilterByCommand filterByCommand, EditNoteCommand editNoteCommand,
                      AddNoteCommand addNoteCommand, DeleteNoteCommand deleteNoteCommand) {
        this.authenticationCommand = authenticationCommand;
        this.registrationCommand = registrationCommand;
        this.openPageCommand = openPageCommand;
        this.filterByCommand = filterByCommand;
        this.editNoteCommand = editNoteCommand;
        this.addNoteCommand = addNoteCommand;
        this.deleteNoteCommand = deleteNoteCommand;
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
