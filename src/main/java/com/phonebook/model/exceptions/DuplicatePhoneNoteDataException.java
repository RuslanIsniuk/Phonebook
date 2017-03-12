package com.phonebook.model.exceptions;


public class DuplicatePhoneNoteDataException extends ServiceException {
    public DuplicatePhoneNoteDataException() {
        super("ERROR! Operation failed! Such client is already exist!");
    }
}
