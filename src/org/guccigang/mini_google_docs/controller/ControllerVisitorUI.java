package org.guccigang.mini_google_docs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.guccigang.mini_google_docs.GuiUtil;
import org.guccigang.mini_google_docs.Main;

public class ControllerVisitorUI {
    private Stage stage;
    private Scene scene;
    public void signUpAction(ActionEvent event) {
        //post-condition: A new window opens on top of the sign in with sign up form.
        //Tis new window is MODAL meaning that it will block all other windows of the application until it is closed.
        try {
            GuiUtil.createModalWindow(event, "views/signUp.fxml");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
