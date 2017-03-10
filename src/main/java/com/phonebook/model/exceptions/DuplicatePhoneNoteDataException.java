package com.phonebook.model.exceptions;

/**
 * Created by Руслан on 08.03.2017.
 */
public class DuplicatePhoneNoteDataException extends ServiceException {
    public DuplicatePhoneNoteDataException(){
        super("ERROR! Operation failed! Such client is already exist!");
    }
}
