package com.phonebook.model.exceptions;

/**
 * Created by Руслан on 08.03.2017.
 */
public class ClientDataIncorrectException extends ServiceException {

    public ClientDataIncorrectException(){
        super("ERROR! Login or password is incorrect!");
    }

    public ClientDataIncorrectException(String errorMessage){
        super(errorMessage);
    }
}
