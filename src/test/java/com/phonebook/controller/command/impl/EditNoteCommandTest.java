package com.phonebook.controller.command.impl;

import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.entities.PhoneNote;
import com.phonebook.model.exceptions.ServiceException;
import com.phonebook.model.services.ClientData;
import com.phonebook.model.services.ClientOperations;
import com.phonebook.model.services.NoteOperations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EditNoteCommandTest {
    private static final String DEFAULT_OPERATION_FAIL_PATH = "/jsp/editNote.jsp";
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/jsp/operationConfirm.jsp";
    EditNoteCommand editNoteCommand;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest request;
    @Mock
    NoteOperations noteOperations;
    @Mock
    ClientData clientData;
    @Mock
    ClientOperations clientOperations;
    @Mock
    Client client;

    @Before
    public void setUp() throws ServiceException, AuthorizationException {
        editNoteCommand = new EditNoteCommand(noteOperations, clientData, clientOperations);
        when(client.getClientID()).thenReturn(1);
        when(request.getSession().getAttribute("clientData")).thenReturn(client);
        when(clientOperations.checkClientID(client, 1)).thenReturn(true);
        when(clientData.getPhonebook(client)).thenReturn(new ArrayList<PhoneNote>());
        when(request.getParameter("noteFirstName")).thenReturn("FirstName");
        when(request.getParameter("noteSecondName")).thenReturn("SecondName");
        when(request.getParameter("noteAdditionalName")).thenReturn("AdditionalName");
        when(request.getParameter("noteMobileNum")).thenReturn("1234546");
        when(request.getParameter("noteHomeNum")).thenReturn("HomeNum");
        when(request.getParameter("noteLocation")).thenReturn("Location");
        when(request.getParameter("noteEmail")).thenReturn("Email");
    }

    @After
    public void tearDown() {
        request = null;
        noteOperations = null;
        clientData = null;
        clientOperations = null;
        client = null;
    }

    @Test(expected = AuthorizationException.class)
    public void executeWithAuthorizationException() throws AuthorizationException {
        when(request.getParameter("clientID")).thenReturn("2");
        when(clientOperations.checkClientID(any(), eq(2))).thenReturn(false);
        editNoteCommand.execute(request);
    }

    @Test
    public void defaultExecute() throws AuthorizationException, ServiceException {
        when(request.getParameter("clientID")).thenReturn("1");
        when(request.getParameter("noteID")).thenReturn("3");
        when(clientOperations.checkClientID(client, 1)).thenReturn(true);
        ArgumentCaptor<PhoneNote> argument = ArgumentCaptor.forClass(PhoneNote.class);

        String actualJsp = editNoteCommand.execute(request);
        assertEquals(actualJsp, DEFAULT_OPERATION_CONFIRM_PATH);
        verify(noteOperations).editNote(eq(3), argument.capture());
    }

    @Test
    public void executeWithWrongData() throws AuthorizationException, ServiceException {
        when(request.getParameter("clientID")).thenReturn("1");
        when(request.getParameter("noteID")).thenReturn("2");
        when(clientOperations.checkClientID(client, 1)).thenReturn(true);
        ArgumentCaptor<PhoneNote> argument = ArgumentCaptor.forClass(PhoneNote.class);
        doThrow(new ServiceException()).when(noteOperations).editNote(eq(2), argument.capture());
        String actualJsp = editNoteCommand.execute(request);
        assertEquals(actualJsp, DEFAULT_OPERATION_FAIL_PATH);
    }
}