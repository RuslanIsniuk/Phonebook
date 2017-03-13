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
import java.util.ArrayList;
import java.util.List;

public class FilterByCommand extends Command {
    private static final Logger logger = Logger.getLogger(FilterByCommand.class);
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/jsp/viewNotes.jsp";
    @Autowired
    private NoteOperations noteOperations;
    @Autowired
    private ClientData clientData;

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        String actionType = request.getParameter("actionType");
        passClientValidation(request);
        Client client = getClientFromSession(request);
        String subString = request.getParameter("subString");
        String pathToJSP;
        List<PhoneNote> phoneNoteList;

        try {
            switch (actionType) {
                case "filterByFirstName":
                    phoneNoteList = noteOperations.filterNotes(subString, client.getClientID(), PhoneNote.noteCompareType.BY_FIRST_NAME);
                    break;
                case "filterBySecondName":
                    phoneNoteList = noteOperations.filterNotes(subString, client.getClientID(), PhoneNote.noteCompareType.BY_SECOND_NAME);
                    break;
                case "filterByMobNum":
                    phoneNoteList = noteOperations.filterNotes(subString, client.getClientID(), PhoneNote.noteCompareType.BY_MOBILE_NUMBER);
                    break;

                default:
                    phoneNoteList = new ArrayList<>();
                    logger.error("Error! Get default case!");
                    break;

            }
            request.setAttribute("phoneNoteList", phoneNoteList);
            request.setAttribute("clientID", client.getClientID());
            pathToJSP = DEFAULT_OPERATION_CONFIRM_PATH;
        } catch (ServiceException se) {
            logger.error(se);
            request.setAttribute("phoneNoteList", clientData.getPhonebook(client));
            request.setAttribute("clientID", client.getClientID());
            request.setAttribute("errorMessage", se.getMessage());
            pathToJSP = DEFAULT_OPERATION_CONFIRM_PATH;
        }
        return pathToJSP;
    }
}
