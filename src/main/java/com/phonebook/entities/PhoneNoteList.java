package com.phonebook.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "notes")
public class PhoneNoteList {
    private List<PhoneNote> noteList;

    public List<PhoneNote> getNoteList() {
        return noteList;
    }

    @XmlElement(name = "note")
    public void setNoteList(List<PhoneNote> phoneNoteList) {
        this.noteList = phoneNoteList;
    }

    public void addNote(PhoneNote phoneNote){
        noteList.add(phoneNote);
    }

    public void removeNote(PhoneNote phoneNote){
        noteList.remove(phoneNote);
    }
}
