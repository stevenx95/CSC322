package org.guccigang.mini_google_docs.controller.TabooControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.guccigang.mini_google_docs.model.GuiUtil;
import org.guccigang.mini_google_docs.model.TabooWordDAO;

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.TextField;
import org.guccigang.mini_google_docs.model.UserObject;

public class OriginalAndVisitorReportTabooWordController {

    private UserObject currentUser;

    @FXML
    TextField tabooWordBar;

    //Default constructor used for visitor
    public OriginalAndVisitorReportTabooWordController(){
        this.currentUser = null;
    }

    //For OU
    public OriginalAndVisitorReportTabooWordController(UserObject currentUser){
        this.currentUser = currentUser;
    }

    private void tabooSuggestionToDB() throws IOException, SQLException {

        String userNameInput = "";

        if(currentUser == null){
            userNameInput = "visitor";
        }
        else {
            userNameInput = currentUser.getUserName();
        }

        TabooWordDAO.sendTabooSuggestion(userNameInput,tabooWordBar.getText());
    }
    public void submitAction(ActionEvent event) {
        try {
            tabooSuggestionToDB();
            GuiUtil.closeWindow(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
