package com.phonebook.model.exceptions;

/**
 * Created by Руслан on 08.03.2017.
 */
public class DuplicateClientDataException extends Exception{

    public DuplicateClientDataException(){
        super("ERROR! Operation failed! Such client is already exist!");
    }
}
