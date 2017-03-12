package com.phonebook.model.exceptions;

public class ClientDataIncorrectException extends ServiceException {

    public ClientDataIncorrectException() {
        super("ERROR! Login or password is incorrect!");
    }

    public ClientDataIncorrectException(String errorMessage) {
        super(errorMessage);
    }
}
