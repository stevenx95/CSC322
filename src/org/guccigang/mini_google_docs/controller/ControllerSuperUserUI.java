package org.guccigang.mini_google_docs.controller;

import javafx.event.ActionEvent;
import org.guccigang.mini_google_docs.GuiUtil;

import java.io.IOException;

public class ControllerSuperUserUI {

    public void signOutButton(ActionEvent event){
        try {

            GuiUtil.createWindow(event,"views/login.fxml", "Gucci Gang");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleTabooList(ActionEvent event){
        try{
            GuiUtil.createWindow(event, "views/tabooSUListView.fxml","Manage Taboo List");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
