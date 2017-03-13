package com.phonebook.controller.command.impl;

import com.phonebook.controller.command.Command;
import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.model.services.ClientOperations;
import com.phonebook.model.services.NoteOperations;

import javax.servlet.http.HttpServletRequest;

public class DeleteNoteCommand extends Command {
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/jsp/operationConfirm.jsp";
    private NoteOperations noteOperations;

    public DeleteNoteCommand() {
    }

    public DeleteNoteCommand(NoteOperations noteOperations, ClientOperations clientOperations) {
        this.noteOperations = noteOperations;
        this.clientOperations = clientOperations;
    }

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
