package com.phonebook.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"noteID", "firstName", "secondName", "additionalName", "mobileNumber", "homeNumber", "location", "email", "noteOwner"})
@XmlRootElement(name = "note")
public class PhoneNote {
    private int noteID;
    private String firstName;
    private String secondName;
    private String additionalName;
    private String mobileNumber;
    private String homeNumber;
    private String location;
    private String email;
    private Client noteOwner;

    public enum noteCompareType {
        BY_FIRST_NAME,
        BY_SECOND_NAME,
        BY_MOBILE_NUMBER
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNote phoneNote = (PhoneNote) o;

        if (noteID != phoneNote.noteID) return false;
        if (firstName != null ? !firstName.equals(phoneNote.firstName) : phoneNote.firstName != null) return false;
        if (secondName != null ? !secondName.equals(phoneNote.secondName) : phoneNote.secondName != null) return false;
        if (additionalName != null ? !additionalName.equals(phoneNote.additionalName) : phoneNote.additionalName != null)
            return false;
        if (mobileNumber != null ? !mobileNumber.equals(phoneNote.mobileNumber) : phoneNote.mobileNumber != null)
            return false;
        if (homeNumber != null ? !homeNumber.equals(phoneNote.homeNumber) : phoneNote.homeNumber != null) return false;
        if (location != null ? !location.equals(phoneNote.location) : phoneNote.location != null) return false;
        if (email != null ? !email.equals(phoneNote.email) : phoneNote.email != null) return false;
        return noteOwner != null ? noteOwner.equals(phoneNote.noteOwner) : phoneNote.noteOwner == null;
    }

    @Override
    public int hashCode() {
        int result = noteID;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (additionalName != null ? additionalName.hashCode() : 0);
        result = 31 * result + (mobileNumber != null ? mobileNumber.hashCode() : 0);
        result = 31 * result + (homeNumber != null ? homeNumber.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (noteOwner != null ? noteOwner.hashCode() : 0);
        return result;
    }

    public int getNoteID() {
        return noteID;
    }

    @XmlElement
    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public Client getNoteOwner() {
        return noteOwner;
    }

    @XmlElement(name = "client")
    public void setNoteOwner(Client noteOwner) {
        this.noteOwner = noteOwner;
    }

    public String getFirstName() {
        return firstName;
    }

    @XmlElement
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    @XmlElement
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    @XmlElement
    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    @XmlElement
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    @XmlElement
    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getLocation() {
        return location;
    }

    @XmlElement
    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }
}
