package org.guccigang.mini_google_docs.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.guccigang.mini_google_docs.model.DbUtil;
import org.guccigang.mini_google_docs.model.GuiUtil;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SignUpController {

    @FXML
    TextField userName, firstName, lastName, interest1, interest2, interest3;

    @FXML
    PasswordField passwordField, passwordCheck;

    public void cancelAction(ActionEvent event) {
        GuiUtil.closeWindow(event);
    }

    public void submitAction(ActionEvent event) {
        //Precondition: program is poperly connected to a database; all entry fields are filled passwordField == passwordCheck
        //Post-condition: a nre user is added to the database and we are redirected to this user's profile
        if(!correctApplication()) {
            return;
        }

       String sql = "INSERT INTO application VALUES (?,?,?,?,?,?,?,0)";
        try {
          int result =  DbUtil.executeUpdateDB(sql, userName.getText(), passwordField.getText(), firstName.getText(),
                  lastName.getText(), interest1.getText(), interest2.getText(), interest3.getText());

          if (result == 0) {
              GuiUtil.createAlertWindow(Alert.AlertType.ERROR, null, "Something went wrong. Please try again", "error");
          }else {
               GuiUtil.createAlertWindow(Alert.AlertType.CONFIRMATION, "Please allow 24 hours for your account to be activated",
                       "Application submitted successfuly!,", "Confirmation");
               GuiUtil.closeWindow(event);
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean correctApplication() {
        boolean correct = true;
        if (!passwordField.getText().equals(passwordCheck.getText())) {
            passwordError();
            correct = false;
        }
        if (userName.getText().isEmpty()) {
            GuiUtil.createAlertWindow(Alert.AlertType.ERROR, "Please enter a username",
                    "username field is empty", "Error");
            correct = false;
        }
        if (!userName.getText().isEmpty() && isTaken(userName.getText())) {
            GuiUtil.createAlertWindow(Alert.AlertType.ERROR, "Please choose a different username",
                    "Username is taken", "Error");
            correct = false;
        }
        if (firstName.getText().isEmpty() || lastName.getText().isEmpty()) {
            GuiUtil.createAlertWindow(Alert.AlertType.ERROR, "Please enter your first and last name.",
                    "First name or Last name is missing", "Error");
            correct = false;
        }

        return correct;
    }

    private boolean isTaken(String userName) {
        ResultSet resultSet = DbUtil.processQuery("SELECT * FROM users WHERE username = ?", userName);
        try {
            if(!resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void passwordError() {
        //post-condition: a pop up window appears saying password don't match. passwordField, passwordCheck are cleared
        GuiUtil.createAlertWindow(Alert.AlertType.ERROR, "Please re-enter your password", "passwords don't match!", "Error");
        passwordField.clear();
        passwordCheck.clear();

    }
}
