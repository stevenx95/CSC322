package org.guccigang.mini_google_docs.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class DocumentFile {


    private StringProperty documentName;
    private StringProperty content;
    private IntegerProperty iD;
    private boolean isLock;
    private IntegerProperty restricted;
    private Date date;
    private StringProperty owner;
    private IntegerProperty counter;

    public DocumentFile(){
        documentName = new SimpleStringProperty("");
        content = new SimpleStringProperty("");;
        iD = new SimpleIntegerProperty(0);;
        isLock = false;
        restricted = new SimpleIntegerProperty(0);
        date = null;
        owner = new SimpleStringProperty("");;
        counter = new SimpleIntegerProperty(0);;
    }

    public DocumentFile(String owner, String documentName, int iD, int version, String content, int isLock, int restricted, Date date, int counter){
        this.documentName = new SimpleStringProperty(documentName);
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

    public String getDocumentName(){
        return documentName.get();
    }
    public void setDocumentName(String name){
        this.documentName.set(name);
    }
    public StringProperty documentNameProperty(){
        return this.documentName;
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


}
