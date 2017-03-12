package com.phonebook.controller.command.impl;

import com.phonebook.controller.command.Command;
import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.model.services.NoteOperations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class DeleteNoteCommand extends Command {
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/jsp/operationConfirm.jsp";
    @Autowired
    private NoteOperations noteOperations;

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        String pathToJSP;
        Client client = getClientFromSession(request);
        passClientValidation(request);
        int noteID = Integer.parseInt(request.getParameter("noteID"));
        noteOperations.deleteNote(noteID);
        request.setAttribute("clientID", client.getClientID());
        request.setAttribute("infoMessage", "Operation done successfully!");
        pathToJSP = DEFAULT_OPERATION_CONFIRM_PATH;
        return pathToJSP;
    }
}
