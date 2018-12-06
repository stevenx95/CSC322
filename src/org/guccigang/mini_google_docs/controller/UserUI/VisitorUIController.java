package org.guccigang.mini_google_docs.controller.UserUI;

import javafx.event.ActionEvent;

import org.guccigang.mini_google_docs.UILocation;
import org.guccigang.mini_google_docs.model.GuiUtil;

import java.io.IOException;

public class VisitorUIController {

    public void signUpAction(ActionEvent event) {
        //post-condition: A new window opens on top of the sign in with sign up form.
        //Tis new window is MODAL meaning that it will block all other windows of the application until it is closed.
        try {
            GuiUtil.createWindow(event, UILocation.SIGNUP, "Sign Up");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signOutButton(ActionEvent event){
        try {

            GuiUtil.createWindowAndDestroy(event,UILocation.LOGIN, "Gucci Gang");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleViewDocumentManager(ActionEvent event){
        try{
            GuiUtil.changeScene(event, UILocation.VISITOR_VIEW_DOCUMENTS,"View Documents");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void reportTabooWordAction(ActionEvent event){
            try {
                GuiUtil.createWindow(event, UILocation.ORIGINAL_AND_VISITOR_REPORT_TABOO_WORD, "Report Taboo Word");
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

}
