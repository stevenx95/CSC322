package org.guccigang.mini_google_docs.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;

public class UserObject {

    final private String userName;
    final private String password;
    final private String firstName;
    final private String lastName;
    final private int membershipLevel;
    private String[] interests;

    public UserObject(String userName, String password, String firstName, String lastName, int membershipLevel) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.membershipLevel = membershipLevel;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String[] getInterests() {
        return interests;
    }
    public void addInterests(String... interests) {
        this.interests = interests;
    }

    public int getMembershipLevel() {
        return membershipLevel;
    }
    public String getMembership(){
        if(getMembershipLevel() == 1){
            return "Ordinary User";
        }else return "Super User";
    }
    public StringProperty userNameProperty(){
        return new SimpleStringProperty(userName.toString());
    }
    public StringProperty membershipProperty(){
        return new SimpleStringProperty(getMembership());
    }
    public StringProperty interestsProperty(){
        return new SimpleStringProperty(Arrays.toString(getInterests()));
    }
}
