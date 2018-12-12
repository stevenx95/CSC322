package org.guccigang.mini_google_docs.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InvitationObject {
    private final int docId;
    private final String owner;
    private final String username;
    private final String docName;
    private final String createdDate;

    public InvitationObject(int docId, String owner, String username, String docName, String createdDate) {
        this.docId = docId;
        this.owner = owner;
        this.username = username;
        this.docName = docName;
        this.createdDate = createdDate;
    }

    public int getDocId() {
        return docId;
    }

    public String getOwner() {
        return owner;
    }

    public String getUsername() {
        return username;
    }

    public String getDocName() {
        return docName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public IntegerProperty getDocIdProperty() {
        return new SimpleIntegerProperty(this.docId);
    }

    public StringProperty getOwnerProperty() {
        return new SimpleStringProperty(this.owner);
    }

    public StringProperty getUserNameProperty() {
        return new SimpleStringProperty(this.username);
    }
    public StringProperty getDocNameProperty() {
        return new SimpleStringProperty(this.docName);
    }
    public  StringProperty getDateProperty() {
        return new SimpleStringProperty(this.createdDate);
    }

}

