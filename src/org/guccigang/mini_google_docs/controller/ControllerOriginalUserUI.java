package org.guccigang.mini_google_docs.controller;

import javafx.event.ActionEvent;
import org.guccigang.mini_google_docs.GuiUtil;

import java.io.IOException;

public class ControllerOriginalUserUI {

    public void signOutButton(ActionEvent event){
        try {

            GuiUtil.createWindow(event,"views/login.fxml", "Gucci Gang");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
