package com.phonebook.entities;

/**
 * Created by Руслан on 06.03.2017.
 */
public class PhoneNote {
    private String firstName;
    private String secondName;
    private String additionalName;
    private String mobileNumber;
    private String homeNumber;
    private String location;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNote phoneNote = (PhoneNote) o;

        if (firstName != null ? !firstName.equals(phoneNote.firstName) : phoneNote.firstName != null) return false;
        if (secondName != null ? !secondName.equals(phoneNote.secondName) : phoneNote.secondName != null) return false;
        if (additionalName != null ? !additionalName.equals(phoneNote.additionalName) : phoneNote.additionalName != null)
            return false;
        if (mobileNumber != null ? !mobileNumber.equals(phoneNote.mobileNumber) : phoneNote.mobileNumber != null)
            return false;
        if (homeNumber != null ? !homeNumber.equals(phoneNote.homeNumber) : phoneNote.homeNumber != null) return false;
        if (location != null ? !location.equals(phoneNote.location) : phoneNote.location != null) return false;
        return email != null ? email.equals(phoneNote.email) : phoneNote.email == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (additionalName != null ? additionalName.hashCode() : 0);
        result = 31 * result + (mobileNumber != null ? mobileNumber.hashCode() : 0);
        result = 31 * result + (homeNumber != null ? homeNumber.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
