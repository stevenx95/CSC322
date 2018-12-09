package org.guccigang.mini_google_docs.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class UserObject {

    final private String userName;
    final private String password;
    final private String firstName;
    final private String lastName;
    final private int membershipLevel;
    final private int DocumentTabooReviewFlag;
    private List<String> interests;

    public UserObject(String userName, String password, String firstName, String lastName, int membershipLevel, int DocumentTabooReviewFlag) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.membershipLevel = membershipLevel;
        this.DocumentTabooReviewFlag = DocumentTabooReviewFlag;
        this.interests = new ArrayList<>();
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

    public List<String> getInterests() {
        return interests;
    }
    public void addInterest(String interest) {
        interests.add(interest);
    }

    public String getInterest(int i) {
        return interests.get(i);
    }

    public int getMembershipLevel() {
        return membershipLevel;
    }
    public String getMembership(){
        if(getMembershipLevel() == 1){
            return "Ordinary User";
        }else return "Super User";
    }
    public int getDocumentTabooReviewFlag () {
        return DocumentTabooReviewFlag;
    }
    public boolean isTabooFlag(){
        if (getDocumentTabooReviewFlag() == 1){
            return true;
        }else return false;
    }
    public StringProperty membershipProperty(){return new SimpleStringProperty(getMembership());}

    public StringProperty userNameProperty() {
        return new SimpleStringProperty(this.userName);
    }

    public StringProperty passwordProperty() {
        return new SimpleStringProperty(this.password);
    }

    public StringProperty firstNameProperty() {
        return new SimpleStringProperty(this.firstName);
    }

    public StringProperty lastNameProperty() {
        return new SimpleStringProperty(this.lastName);
    }
    public IntegerProperty membershipLevelProperty() {
        return new SimpleIntegerProperty(this.membershipLevel);
     }

     public StringProperty interestProperty(int i) {
        return new SimpleStringProperty(this.interests.toString());
     }
}
