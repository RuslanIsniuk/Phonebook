package com.phonebook.parser;

import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Created by Руслан on 06.03.2017.
 */
public interface Parser {
    Object getObject(File file, Class c) throws JAXBException;
    void saveObject(File file, Object o) throws JAXBException;
}
