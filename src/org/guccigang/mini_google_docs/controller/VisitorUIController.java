package org.guccigang.mini_google_docs.controller;

import javafx.event.ActionEvent;

import org.guccigang.mini_google_docs.GuiUtil;

import java.io.IOException;

public class VisitorUIController {

    public void signUpAction(ActionEvent event) {
        //post-condition: A new window opens on top of the sign in with sign up form.
        //Tis new window is MODAL meaning that it will block all other windows of the application until it is closed.
        try {
            GuiUtil.createWindow(event, "views/signUp.fxml", "Sign Up");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signOutButton(ActionEvent event){
        try {

            GuiUtil.createWindowAndDestroy(event,"views/login.fxml", "Gucci Gang");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleViewDocumentManager(ActionEvent event){
        try{
            GuiUtil.changeScene(event, "views/VisitorViewDocuments.fxml","View Documents");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
