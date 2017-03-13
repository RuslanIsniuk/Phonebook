package com.phonebook.controller.command.impl;

import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.Client;
import com.phonebook.model.exceptions.ClientDataIncorrectException;
import com.phonebook.model.exceptions.ServiceException;
import com.phonebook.model.services.ClientOperations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationCommandTest {
    private static final String DEFAULT_OPERATION_FAIL_PATH = "/index.jsp";
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/jsp/mainPage.jsp";
    AuthenticationCommand authenticationCommand;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest request;
    @Mock
    OpenPageCommand openPageCommand;
    @Mock
    ClientOperations clientOperations;
    @Mock
    Client client;


    @Before
    public void setUp()  throws ClientDataIncorrectException{
        authenticationCommand = new AuthenticationCommand(openPageCommand,clientOperations);
        when(request.getParameter("username")).thenReturn("testLogin");
        when(request.getParameter("password")).thenReturn("testPass");
    }

    @After
    public void tearDown(){
        request = null;
        openPageCommand = null;
        clientOperations = null;
        client = null;
    }

    @Test
    public void defaultExecute() throws AuthorizationException, ServiceException {
        when(clientOperations.authentication("testLogin","testPass")).thenReturn(client);
        when(openPageCommand.execute(request)).thenReturn("/jsp/mainPage.jsp");
        String actualJsp = authenticationCommand.execute(request);
        assertEquals(actualJsp,DEFAULT_OPERATION_CONFIRM_PATH);
    }

    @Test
    public void executeWithWrongData() throws AuthorizationException, ServiceException {
        when(clientOperations.authentication("testLogin","testPass")).thenThrow(new ServiceException());
        String actualJsp = authenticationCommand.execute(request);
        assertEquals(actualJsp,DEFAULT_OPERATION_FAIL_PATH);
    }
}