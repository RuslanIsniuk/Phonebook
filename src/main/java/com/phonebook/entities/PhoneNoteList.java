package com.phonebook.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "notes")
public class PhoneNoteList {
    private List<PhoneNote> noteList;

    public List<PhoneNote> getClientList() {
        return noteList;
    }

    @XmlElement(name = "note")
    public void setClientList(List<PhoneNote> phoneNoteList) {
        this.noteList = phoneNoteList;
    }
}
