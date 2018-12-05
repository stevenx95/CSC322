package org.guccigang.mini_google_docs.controller;


import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.UserObject;

public class SuperAndOriginalTextEditorController
{
    private UserObject currentUser;
    private DocumentFile documentFile;

    SuperAndOriginalTextEditorController(){
        currentUser = null;
        documentFile = null;
    }
    SuperAndOriginalTextEditorController(UserObject currentUser, DocumentFile documentFile){
        this.currentUser = currentUser;
        this.documentFile = documentFile;
    }

    public void handleSave(){

    }

}
