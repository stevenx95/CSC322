package org.guccigang.mini_google_docs.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.util.Date;

public class DocumentFile {

    private IntegerProperty iD;
    private StringProperty owner;
    private StringProperty documentName;
    private StringProperty content;
    private boolean isLock;
    private IntegerProperty restricted;
   // private Date dateCreated;
    private boolean tabooFlag;
    private IntegerProperty counter;


    public DocumentFile(){
        iD = new SimpleIntegerProperty(0);
        owner = new SimpleStringProperty("");
        documentName = new SimpleStringProperty("");
        content = new SimpleStringProperty("");
        isLock = false;
        restricted = new SimpleIntegerProperty(0);
//        dateCreated = new Date("");
        counter = new SimpleIntegerProperty(0);;
    }

    public DocumentFile(String owner, int iD, String documentName, String content, int isLock, int restricted, Date dateCreated, int counter){
        this.iD = new SimpleIntegerProperty(iD);
        this.owner = new SimpleStringProperty(owner);
        this.documentName = new SimpleStringProperty(documentName);
        this.content = new SimpleStringProperty(content);
        if(isLock == 0){
            this.isLock = false;
        }else
            this.isLock = true;
        this.restricted = new SimpleIntegerProperty(restricted);
       // this.dateCreated = dateCreated;
        this.counter = new SimpleIntegerProperty(counter);



    }

    public String getOwner(){
        return owner.get();
    }
    public void setOwner(String owner){
        this.owner.set(owner);
    }
    public StringProperty ownerProperty(){
        return this.owner;
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

//    public void setDate(Date date){
//        this.date = date;
//    }
//    public Date getDate(){
//        return this.date;
//    }

    public int getCounter() {
        return this.counter.get();
    }
    public IntegerProperty counterProperty(){
        return this.counter;
    }
    public void setCounter(int counter){
        this.counter.set(counter);
    }

    public void setDocumentName(String documentName) {
        this.documentName.set(documentName);
    }

    public String getDocumentName() {
        return documentName.get();
    }

    public StringProperty documentNameProperty() {
        return documentName;
    }

    public int getiD() {
        return iD.get();
    }

    public IntegerProperty iDProperty() {
        return this.iD;
    }

    public void setiD(int iD) {
        this.iD.set(iD);
    }

    public boolean isLock() {
        return this.isLock;
    }
    public StringProperty restrictedProperty() {
        return new SimpleStringProperty(restricted.toString());
    }

    public void setTabooFlag(int tabooFlag) {
        if(tabooFlag == 0){
            this.tabooFlag = false;
        }else this.tabooFlag = true;    }

    public boolean isTabooFlag() {
        return tabooFlag;
    }

}
