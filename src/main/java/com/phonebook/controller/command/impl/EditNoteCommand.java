package com.phonebook.controller.command.impl;

import com.phonebook.controller.command.Command;
import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.entities.PhoneNote;
import com.phonebook.model.exceptions.ServiceException;
import com.phonebook.model.services.ClientData;
import com.phonebook.model.services.NoteOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class EditNoteCommand extends Command {
    private static final Logger logger = Logger.getLogger(FilterByCommand.class);
    @Autowired
    private NoteOperations noteOperations;
    @Autowired
    private ClientData clientData;

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
            pathToJSP = "/jsp/operationConfirm.jsp";
        } catch (ServiceException se) {
            logger.error(se);
            request.setAttribute("clientID", client.getClientID());
            request.setAttribute("phoneNoteList", clientData.getPhonebook(client));
            request.setAttribute("errorMessage", se.getMessage());
            pathToJSP = "/jsp/editNote.jsp";
        }
        return pathToJSP;
    }

}
