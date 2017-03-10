package com.phonebook.model.exceptions;

/**
 * Created by Руслан on 09.03.2017.
 */
public class ServiceException extends Exception {
    public ServiceException() {
        super("Error in some service method!");
    }

    public ServiceException(String error) {
        super(error);
    }
}
