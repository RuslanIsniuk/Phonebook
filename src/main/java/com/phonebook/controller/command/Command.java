package com.phonebook.controller.command;

import com.phonebook.model.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Руслан on 09.03.2017.
 */
public interface Command {
    String execute(HttpServletRequest request) throws ServiceException;
}
