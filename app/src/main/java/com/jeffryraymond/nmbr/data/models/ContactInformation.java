package com.jeffryraymond.nmbr.data.models;

/**
 * Created by Jeffry Raymond on 2018-11-19.
 */

public class ContactInformation {
    private String contactID;
    private String contactInfo;
    private String contactNumber;

    public ContactInformation(){}

    public ContactInformation(String contactId, String contactName, String phoneNumber){
        this.contactID = contactId;
        this.contactInfo = contactName;
        this.contactNumber = phoneNumber;
    }

    //Getters and Setters for contact ID
    public String getContactID(){
        return contactID;
    }
    public void setContactID(String cID){
        this.contactID = cID;
    }

    //Getters and Setters for contact's name
    public String getContactInfo() {
        return contactInfo;
    }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    //Getters and Setters for contact's number
    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
