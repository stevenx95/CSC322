package org.guccigang.mini_google_docs.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.util.Date;

public class DocumentFile {


    private StringProperty userName;
    private StringProperty content;
    private IntegerProperty iD;
    private boolean isLock;
    private IntegerProperty restricted;
    private Date date;
    private StringProperty owner;
    private IntegerProperty counter;

    //steven fix
    private IntegerProperty publicFlag;
    private IntegerProperty version;



    public DocumentFile(){
        userName = new SimpleStringProperty("");
        publicFlag = new SimpleIntegerProperty(0);
        content = new SimpleStringProperty("");
        iD = new SimpleIntegerProperty(0);
        version = new SimpleIntegerProperty(0);
        isLock = false;
        restricted = new SimpleIntegerProperty(0);
        date = null;
        owner = new SimpleStringProperty("");;
        counter = new SimpleIntegerProperty(0);;
    }

    public DocumentFile(String owner, String userName, int iD, int version, String content, int isLock, int restricted, Date date, int counter){
        this.userName = new SimpleStringProperty(userName);
        this.content = new SimpleStringProperty(content);
        this.iD = new SimpleIntegerProperty(iD);
        this.date = date;
        this.owner = new SimpleStringProperty(owner);
        this.counter = new SimpleIntegerProperty(counter);

        if(isLock == 0){
            this.isLock = false;
        }else this.isLock = true;

        this.restricted = new SimpleIntegerProperty(restricted);
    }

    public String getUserName(){
        return userName.get();
    }
    public void setUserName(String name){
        this.userName.set(name);
    }
    public StringProperty userNameProperty(){
        return this.userName;
    }

    public String getContent(){
        return this.content.get();
    }
    public void setContent(String content){
        this.content.set(content);
    }
    public StringProperty contentProperty() {
        return content;
    }

    public int getID(){
        return this.iD.get();
    }
    public void setID(int iD){
        this.iD.set(iD);
    }

    public void setLock(int isLock){
        if(isLock == 0){
            this.isLock = false;
        }else this.isLock = true;
    }
    public boolean getLock(){
        return this.isLock;
    }

    public void setRestricted(int restricted){
        this.restricted.set(restricted);
    }
    public int getRestricted(){
        return this.restricted.get();
    }

    public void setDate(Date date){
        this.date = date;
    }
    public Date getDate(){
        return this.date;
    }

    public String getOwner(){
        return this.owner.get();
    }
    public void setOwner(String owner){
        this.owner.set(owner);
    }
    public StringProperty ownerProperty(){
        return this.owner;
    }

    public int getCounter() {
        return this.counter.get();
    }
    public IntegerProperty counterProperty(){
        return this.counter;
    }
    public void setCounter(int counter){
        this.counter.set(counter);
    }




//steven fix
    public int getiD() {
        return iD.get();
    }

    public IntegerProperty iDProperty() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD.set(iD);
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public IntegerProperty restrictedProperty() {
        return restricted;
    }

    public int getPublicFlag() {
        return publicFlag.get();
    }

    public IntegerProperty publicFlagProperty() {
        return publicFlag;
    }

    public void setPublicFlag(int publicFlag) {
        this.publicFlag.set(publicFlag);
    }

    public int getVersion() {
        return version.get();
    }

    public IntegerProperty versionProperty() {
        return version;
    }

    public void setVersion(int version) {
        this.version.set(version);
    }

    public ObservableValue<String> documentNameProperty() {
        return userName;
    }
}
