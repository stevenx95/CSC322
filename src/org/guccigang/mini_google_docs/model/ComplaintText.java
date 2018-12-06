package org.guccigang.mini_google_docs.model;

public class ComplaintText {

    private int complaintId;
    private int docID;
    private int version;
    private String Owner;
    private String complainer;
    private String message;


    public ComplaintText(){}

    public ComplaintText(int complaintId, int docID, int version, String owner, String complainer, String message) {
        this.complaintId = complaintId;
        this.docID = docID;
        this.version = version;
        Owner = owner;
        this.complainer = complainer;
        this.message = message;
    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public int getDocID() {
        return docID;
    }

    public void setDocID(int docID) {
        this.docID = docID;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getComplainer() {
        return complainer;
    }

    public void setComplainer(String complainer) {
        this.complainer = complainer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
