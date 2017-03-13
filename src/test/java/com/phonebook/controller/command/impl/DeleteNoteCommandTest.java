package com.phonebook.controller.command.impl;

import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteNoteCommandTest {
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/jsp/operationConfirm.jsp";
    DeleteNoteCommand deleteNoteCommand;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest request;
    @Mock
    NoteOperations noteOperations;
    @Mock
    ClientOperations clientOperations;
    @Mock
    Client client;

    @Before
    public void setUp() {
        deleteNoteCommand = new DeleteNoteCommand(noteOperations, clientOperations);
        when(client.getClientID()).thenReturn(1);
        when(request.getSession().getAttribute("clientData")).thenReturn(client);
        when(request.getParameter("noteID")).thenReturn("2");
    }

    @After
    public void tearDown() {
        request = null;
        noteOperations = null;
        clientOperations = null;
        client = null;
    }

    @Test(expected = AuthorizationException.class)
    public void executeWithAuthorizationException() throws AuthorizationException {
        when(request.getParameter("clientID")).thenReturn("2");
        when(clientOperations.checkClientID(any(), eq(2))).thenReturn(false);
        deleteNoteCommand.execute(request);
    }

    @Test
    public void defaultExecute() throws AuthorizationException {
        when(request.getParameter("clientID")).thenReturn("1");
        when(clientOperations.checkClientID(client, 1)).thenReturn(true);
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        String actualJsp = deleteNoteCommand.execute(request);
        assertEquals(actualJsp, DEFAULT_OPERATION_CONFIRM_PATH);
        verify(noteOperations).deleteNote(argument.capture());

    }
}