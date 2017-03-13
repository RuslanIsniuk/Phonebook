package com.phonebook.controller.command.impl;

import com.phonebook.controller.exceptions.AuthorizationException;
import com.phonebook.entities.PhoneNote;
import com.phonebook.model.exceptions.ServiceException;
import com.phonebook.model.services.ClientOperations;
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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationCommandTest {
    private static final String DEFAULT_OPERATION_FAIL_PATH = "/jsp/registration.jsp";
    private static final String DEFAULT_OPERATION_CONFIRM_PATH = "/index.jsp";
    RegistrationCommand registrationCommand;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HttpServletRequest request;
    @Mock
    ClientOperations clientOperations;

    @Before
    public void setUp() {
        registrationCommand = new RegistrationCommand(clientOperations);
        when(request.getParameter("username")).thenReturn("testLog");
        when(request.getParameter("password")).thenReturn("testPass");
        when(request.getParameter("clientFullName")).thenReturn("clientFullTest");

    }

    @After
    public void tearDown() {
        request = null;
        clientOperations = null;
    }

    @Test
    public void defaultExecute() throws ServiceException {
        String actualJsp = registrationCommand.execute(request);
        assertEquals(actualJsp, DEFAULT_OPERATION_CONFIRM_PATH);
        verify(clientOperations).registration(eq("testLog"), eq("testPass"), eq("clientFullTest"));
    }

    @Test
    public void executeWithWrongData() throws ServiceException {
        doThrow(new ServiceException()).when(clientOperations).registration("testLog", "testPass", "clientFullTest");
        String actualJsp = registrationCommand.execute(request);
        assertEquals(actualJsp, DEFAULT_OPERATION_FAIL_PATH);
    }
}