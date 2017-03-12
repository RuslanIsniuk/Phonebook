package com.phonebook.controller.command.impl;

import com.phonebook.controller.command.Command;
import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.entities.PhoneNote;
import com.phonebook.model.services.ClientData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class OpenPageCommand extends Command {
    private static final Logger logger = Logger.getLogger(OpenPageCommand.class);
    private static final String clientIDAtrStr = "clientID";
    private String pathToJsp;
    @Autowired
    private ClientData clientData;

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        String actionType = request.getParameter("actionType");
        Client client = getClientFromSession(request);
        int noteID;

        switch (actionType) {
            case "openRegistrationPage":
                pathToJsp = "/jsp/registration.jsp";
                break;

            case "authentication":
                if (!request.isRequestedSessionIdValid()) {
                    throw new AuthorizationException();
                }
                request.setAttribute("clientName", client.getClientFullName());
                request.setAttribute(clientIDAtrStr, client.getClientID());
                pathToJsp = "/jsp/mainPage.jsp";
                break;

            case "returnToMainPage":
                passClientValidation(request);

                request.setAttribute("clientName", client.getClientFullName());
                request.setAttribute(clientIDAtrStr, client.getClientID());
                pathToJsp = "/jsp/mainPage.jsp";
                break;

            case "viewPhonebook":
                passClientValidation(request);
                request.setAttribute(clientIDAtrStr, client.getClientID());
                request.setAttribute("phoneNoteList", clientData.getPhonebook(client));
                pathToJsp = "/jsp/viewNotes.jsp";
                break;

            case "openAddNotePage":
                passClientValidation(request);
                request.setAttribute(clientIDAtrStr, client.getClientID());
                pathToJsp = "/jsp/addNote.jsp";
                break;

            case "openEditNotePage":
                noteID = getNoteIDFromRequest(request);
                passClientValidation(request);
                request.setAttribute(clientIDAtrStr, client.getClientID());
                request.setAttribute("phoneNote", clientData.getNote(noteID));
                pathToJsp = "/jsp/editNote.jsp";
                break;

            case "openDeleteNotePage":
                noteID = getNoteIDFromRequest(request);
                passClientValidation(request);
                PhoneNote phoneNote = clientData.getNote(noteID);
                request.setAttribute(clientIDAtrStr, client.getClientID());
                request.setAttribute("phoneNote", phoneNote);
                pathToJsp = "/jsp/deleteNote.jsp";
                break;

            default:
                logger.error("Error! Get default case!");
                break;
        }
        return pathToJsp;
    }

}
