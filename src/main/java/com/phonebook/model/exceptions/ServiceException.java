package com.phonebook.model.exceptions;

public class ServiceException extends Exception {
    public ServiceException() {
        super("Error in some service method!");
    }

    public ServiceException(String error) {
        super(error);
    }
}
