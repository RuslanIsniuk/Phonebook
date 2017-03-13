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

public class EditNoteCommand extends Command {
    private static final Logger logger = Logger.getLogger(EditNoteCommand.class);
    private static final String DEFAULT_OPERATION_FAIL_PATH = "/jsp/editNote.jsp";
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/jsp/operationConfirm.jsp";
    private NoteOperations noteOperations;
    private ClientData clientData;

    public EditNoteCommand() {
    }

    public EditNoteCommand(NoteOperations noteOperations, ClientData clientData, ClientOperations clientOperations) {
        this.noteOperations = noteOperations;
        this.clientData = clientData;
        this.clientOperations = clientOperations;
    }

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        String pathToJSP;
        Client client = getClientFromSession(request);
        passClientValidation(request);

        int noteID = Integer.parseInt(request.getParameter("noteID"));
        PhoneNote phoneNoteNew = new PhoneNote();
        phoneNoteNew.setNoteID(noteID);
        phoneNoteNew.setFirstName(request.getParameter("noteFirstName"));
        phoneNoteNew.setSecondName(request.getParameter("noteSecondName"));
        phoneNoteNew.setAdditionalName(request.getParameter("noteAdditionalName"));
        phoneNoteNew.setMobileNumber(request.getParameter("noteMobileNum"));
        phoneNoteNew.setHomeNumber(request.getParameter("noteHomeNum"));
        phoneNoteNew.setLocation(request.getParameter("noteLocation"));
        phoneNoteNew.setEmail(request.getParameter("noteEmail"));

        try {
            noteOperations.editNote(noteID, phoneNoteNew);
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
