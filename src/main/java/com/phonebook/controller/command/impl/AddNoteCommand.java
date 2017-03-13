package com.phonebook.controller.command.impl;

import com.phonebook.controller.command.Command;
import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.entities.PhoneNote;
import com.phonebook.model.exceptions.ServiceException;
import com.phonebook.model.services.ClientData;
import com.phonebook.model.services.ClientOperations;
import com.phonebook.model.services.NoteOperations;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddNoteCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddNoteCommand.class);
    private static final String DEFAULT_OPERATION_FAIL_PATH = "/jsp/editNote.jsp";
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/jsp/operationConfirm.jsp";
    private NoteOperations noteOperations;
    private ClientData clientData;

    public AddNoteCommand() {
    }

    public AddNoteCommand(NoteOperations noteOperations, ClientData clientData, ClientOperations clientOperations) {
        this.noteOperations = noteOperations;
        this.clientData = clientData;
        this.clientOperations = clientOperations;
    }

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        String pathToJSP;
        Client client = getClientFromSession(request);
        passClientValidation(request);

        PhoneNote phoneNoteNew = new PhoneNote();
        phoneNoteNew.setFirstName(request.getParameter("noteFirstName"));
        phoneNoteNew.setSecondName(request.getParameter("noteSecondName"));
        phoneNoteNew.setAdditionalName(request.getParameter("noteAdditionalName"));
        phoneNoteNew.setMobileNumber(request.getParameter("noteMobileNum"));
        phoneNoteNew.setHomeNumber(request.getParameter("noteHomeNum"));
        phoneNoteNew.setLocation(request.getParameter("noteLocation"));
        phoneNoteNew.setEmail(request.getParameter("noteEmail"));

        try {
            noteOperations.addNote(phoneNoteNew, client);
            request.setAttribute("clientID", client.getClientID());
            request.setAttribute("infoMessage", "Operation done successfully!");
            pathToJSP = DEFAULT_OPERATION_CONFIRM_PATH;
        } catch (ServiceException se) {
            logger.error(se);
            request.setAttribute("clientID", client.getClientID());
            request.setAttribute("phoneNoteList", clientData.getPhonebook(client));
            request.setAttribute("errorMessage", se.getMessage());
            pathToJSP = DEFAULT_OPERATION_FAIL_PATH;
        }
        return pathToJSP;
    }
}
