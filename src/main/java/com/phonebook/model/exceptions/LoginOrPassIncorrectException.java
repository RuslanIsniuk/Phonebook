package com.phonebook.model.exceptions;

/**
 * Created by Руслан on 08.03.2017.
 */
public class LoginOrPassIncorrectException extends Exception {

    public LoginOrPassIncorrectException(){
        super("ERROR! Login or password is incorrect!");
    }
}
