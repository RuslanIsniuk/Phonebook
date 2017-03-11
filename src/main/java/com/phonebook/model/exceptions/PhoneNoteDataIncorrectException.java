package com.phonebook.model.exceptions;


public class PhoneNoteDataIncorrectException extends ServiceException{
    public PhoneNoteDataIncorrectException(){
        super("ERROR! Data is incorrect!");
    }

    public PhoneNoteDataIncorrectException(String errorMessage){
        super(errorMessage);
    }
}
