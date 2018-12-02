package org.guccigang.mini_google_docs.controller;

import javafx.event.ActionEvent;

import org.guccigang.mini_google_docs.GuiUtil;

import java.io.IOException;

public class OriginalUserUIController {

    public void signOutButton(ActionEvent event){
        try {
            GuiUtil.createWindowAndDestroy(event,"views/login.fxml", "Gucci Gang");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openDocumentManager(ActionEvent event){
        try{
            GuiUtil.createWindowAndDestroy(event, "views/SuperAndOriginalDocManager.fxml","Document Manager");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void reportTabooWordAction(ActionEvent event){
        try {
            GuiUtil.createWindow(event, "views/OriginalAndVisitorReportTabooWord.fxml", "Report Taboo Word");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}