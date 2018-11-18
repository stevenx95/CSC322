package org.guccigang.mini_google_docs.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.guccigang.mini_google_docs.DbUtil;
import org.guccigang.mini_google_docs.GuiUtil;


public class SignUpController {

    @FXML
    TextField userName, firstName, lastName, interest1, interest2, interest3;

    @FXML
    PasswordField passwordField, passwordCheck;

    public void cancelAction(ActionEvent event) {
        //post-condition: The window from which the event is called is closed
        Node node = (Node) event.getSource();
        //this variable are used to manipulate the IU.
        Stage window = (Stage) node.getScene().getWindow();
        window.close();
    }

    public void submitAction(ActionEvent event) {
        //Precondition: program is poperly connected to a database; all entry fields are filled passwordField == passwordCheck
        //Post-condition: a nre user is added to the database and we are redirected to this user's profile
        if (!passwordField.getText().equals(passwordCheck.getText())) {
            fixPassword();
            return;
        }

       String sql = "INSERT INTO users VALUES (?,?,?,?,?,?,?,1)";
        try {
          int result =  DbUtil.executeUpdateDB(sql, userName.getText(), passwordField.getText(), firstName.getText(),
                  lastName.getText(), interest1.getText(), interest2.getText(), interest3.getText());

          if (result == 0) {
              GuiUtil.popupWindow(Alert.AlertType.ERROR, null, "Something went wrong. Please try again", "error");
          }else {
               GuiUtil.createWindow(event, "views/visitorUI.fxml", "visitor");
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fixPassword() {
        //post-condition: a pop up window appears saying password don't match. passwordField, passwordCheck are cleared
        GuiUtil.popupWindow(Alert.AlertType.ERROR, "Please re-enter your password", "passwords don't match!", "Error");
        passwordField.clear();
        passwordCheck.clear();

    }
}
