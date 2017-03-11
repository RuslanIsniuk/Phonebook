package com.phonebook.controller.command.impl;

import com.phonebook.controller.command.Command;
import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.model.services.NoteOperations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Руслан on 11.03.2017.
 */
public class DeleteNoteCommand extends Command {
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
        pathToJSP = "/jsp/operationConfirm.jsp";
        return pathToJSP;
    }
}
