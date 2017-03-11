package com.phonebook.controller.command.impl;

import com.phonebook.controller.command.Command;
import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.entities.PhoneNote;
import com.phonebook.model.services.NoteOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class FilterByCommand extends Command {
    private static final Logger logger = Logger.getLogger(FilterByCommand.class);
    @Autowired
    private NoteOperations noteOperations;

    @Override
    public String execute(HttpServletRequest request) throws AuthorizationException {
        String actionType = request.getParameter("actionType");
        passClientValidation(request);
        Client client = getClientFromSession(request);
        String subString = request.getParameter("subString");
        String pathToJSP;
        List<PhoneNote> phoneNoteList;

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
        pathToJSP = "/jsp/viewNotes.jsp";
        return pathToJSP;
    }
}
