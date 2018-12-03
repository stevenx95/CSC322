package org.guccigang.mini_google_docs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.guccigang.mini_google_docs.GuiUtil;
import org.guccigang.mini_google_docs.model.UserObject;

import java.io.IOException;

public class SearchUsersWindowController {

    private UserObject currentUser;

    SearchUsersWindowController(UserObject currentUser){
        this.currentUser = currentUser;
    }
    @FXML
    public void handleHome(ActionEvent event){
        try{
            if(currentUser.getMembershipLevel()==1){
                OriginalUserUIController controller = new OriginalUserUIController(currentUser);
                GuiUtil.changeScene(event,"views/originalUserUI.fxml","Home", controller);
            }else {
                SuperUserUIController controller = new SuperUserUIController(currentUser);
                GuiUtil.changeScene(event,"views/superUserUI.fxml","Home", controller);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
