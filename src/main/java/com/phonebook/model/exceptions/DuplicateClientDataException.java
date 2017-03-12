package com.phonebook.model.exceptions;


public class DuplicateClientDataException extends ServiceException {

    public DuplicateClientDataException() {
        super("ERROR! Operation failed! Such client is already exist!");
    }
}
