package com.phonebook.parser;

import javax.xml.bind.JAXBException;
import java.io.File;


public interface Parser {
    Object getFromFile(File file, Class c) throws JAXBException;

    void writeInFile(File file, Object o) throws JAXBException;
}
