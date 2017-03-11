package com.phonebook.controller.exceptions;

/**
 * Created by Руслан on 10.03.2017.
 */
public class AuthorizationException extends Exception {
    public AuthorizationException() {
        super("Error! Session time ran out!");
    }

    public AuthorizationException(String errorMessage) {
        super(errorMessage);
    }
}
