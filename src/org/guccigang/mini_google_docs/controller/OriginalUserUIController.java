package org.guccigang.mini_google_docs.controller;

import javafx.event.ActionEvent;

import org.guccigang.mini_google_docs.GuiUtil;
import org.guccigang.mini_google_docs.model.UserObject;

import java.io.IOException;

public class OriginalUserUIController {
    private UserObject user;

    public OriginalUserUIController(UserObject user) {
        this.user = user;
    }

    public OriginalUserUIController() {
        this(null);
    }
    public void signOutButton(ActionEvent event){
        try {
            GuiUtil.createWindowAndDestroy(event,"views/login.fxml", "Gucci Gang");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openDocumentManager(ActionEvent event){
        try{
            SuperAndOriginalDocManagerController controller = new SuperAndOriginalDocManagerController(user);
            GuiUtil.changeScene(event, "views/SuperAndOriginalDocManager.fxml","Document Manager",controller);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void handleFindUser(ActionEvent event){
        try{
            SearchUsersWindowController controller = new SearchUsersWindowController(user);
            GuiUtil.changeScene(event,"views/SearchUsersWindow.fxml", "Search Users",controller);
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