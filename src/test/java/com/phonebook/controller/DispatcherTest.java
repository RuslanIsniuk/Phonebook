package com.phonebook.controller;

import com.phonebook.controller.command.impl.*;
import com.phonebook.controller.exceptions.AuthorizationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DispatcherTest {
    private static final String DEFAULT_OPERATION_FAIL_PATH = "/index.jsp";
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "Execute successful";
    Dispatcher dispatcher;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest request;
    @Mock
    AuthenticationCommand authenticationCommand;
    @Mock
    RegistrationCommand registrationCommand;
    @Mock
    OpenPageCommand openPageCommand;
    @Mock
    FilterByCommand filterByCommand;
    @Mock
    EditNoteCommand editNoteCommand;
    @Mock
    AddNoteCommand addNoteCommand;
    @Mock
    DeleteNoteCommand deleteNoteCommand;

    @Before
    public void setUp() {
        dispatcher = new Dispatcher(authenticationCommand, registrationCommand, openPageCommand, filterByCommand,
                editNoteCommand, addNoteCommand, deleteNoteCommand);
    }

    @After
    public void tearDown() {
        request = null;
        authenticationCommand = null;
        registrationCommand = null;
        openPageCommand = null;
        filterByCommand = null;
        editNoteCommand = null;
        addNoteCommand = null;
        deleteNoteCommand = null;
    }

    @Test
    public void openPage() throws AuthorizationException {
        when(request.getParameter("actionType")).thenReturn("openRegistrationPage");
        when(openPageCommand.execute(request)).thenReturn("Execute successful");
        String actualJsp = dispatcher.logicIdentificator(request);
        assertEquals(actualJsp, DEFAULT_OPERATION_CONFIRM_PATH);
    }

    @Test
    public void authenticationCommand() throws AuthorizationException {
        when(request.getParameter("actionType")).thenReturn("authentication");
        when(authenticationCommand.execute(request)).thenReturn("Execute successful");
        String actualJsp = dispatcher.logicIdentificator(request);
        assertEquals(actualJsp, DEFAULT_OPERATION_CONFIRM_PATH);
    }

    @Test
    public void registrationCommand() throws AuthorizationException {
        when(request.getParameter("actionType")).thenReturn("registration");
        when(registrationCommand.execute(request)).thenReturn("Execute successful");
        String actualJsp = dispatcher.logicIdentificator(request);
        assertEquals(actualJsp, DEFAULT_OPERATION_CONFIRM_PATH);
    }

    @Test
    public void filterByCommand() throws AuthorizationException {
        when(request.getParameter("actionType")).thenReturn("filterByFirstName");
        when(filterByCommand.execute(request)).thenReturn("Execute successful");
        String actualJsp = dispatcher.logicIdentificator(request);
        assertEquals(actualJsp, DEFAULT_OPERATION_CONFIRM_PATH);
    }

    @Test
    public void editNoteCommand() throws AuthorizationException {
        when(request.getParameter("actionType")).thenReturn("editNoteConfirm");
        when(editNoteCommand.execute(request)).thenReturn("Execute successful");
        String actualJsp = dispatcher.logicIdentificator(request);
        assertEquals(actualJsp, DEFAULT_OPERATION_CONFIRM_PATH);
    }

    @Test
    public void addNoteCommand() throws AuthorizationException {
        when(request.getParameter("actionType")).thenReturn("addNoteConfirm");
        when(addNoteCommand.execute(request)).thenReturn("Execute successful");
        String actualJsp = dispatcher.logicIdentificator(request);
        assertEquals(actualJsp, DEFAULT_OPERATION_CONFIRM_PATH);
    }

    @Test
    public void deleteNoteCommand() throws AuthorizationException {
        when(request.getParameter("actionType")).thenReturn("deleteNoteConfirm");
        when(deleteNoteCommand.execute(request)).thenReturn("Execute successful");
        String actualJsp = dispatcher.logicIdentificator(request);
        assertEquals(actualJsp, DEFAULT_OPERATION_CONFIRM_PATH);
    }

    @Test
    public void throwWrongData() throws AuthorizationException {
        when(request.getParameter("actionType")).thenReturn("deleteNoteConfirm");
        when(deleteNoteCommand.execute(request)).thenThrow(new AuthorizationException());
        String actualJsp = dispatcher.logicIdentificator(request);
        assertEquals(actualJsp, DEFAULT_OPERATION_FAIL_PATH);
    }
}