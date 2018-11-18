package org.guccigang.mini_google_docs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.guccigang.mini_google_docs.DbUtil;
import org.guccigang.mini_google_docs.GuiUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignUpController {
    //this variable are used to manipulate the IU.
    private Stage window;
    private Scene scene;

    //these variables are used to query from database;
    private Connection connection;
    private PreparedStatement preparedStatement = null;
    private DbUtil dbUtil;
    ResultSet resultSet;

    @FXML
    TextField userName, firstName, lastName, interest1, interest2, interest3;

    @FXML
    PasswordField passwordField, passwordCheck;

    public SignUpController () {
        dbUtil = new DbUtil();
        //connection = DBUtil.connectDB();
    }

    public void cancelAction(ActionEvent event) {
        //post-condition: The window from which the event is called is closed
        Node node = (Node) event.getSource();
        window = (Stage) node.getScene().getWindow();
        window.close();
    }

    public void submitAction(ActionEvent event) {
        //Precondition: program is poperly connected to a database; all entry fields are filled passwordField == passwordCheck
        //Post-condition: a nre user is added to the database and we are redirected to this user's profile
        if (!passwordField.getText().equals(passwordCheck.getText())) {
            fixPassword();
            return;
        }

        dbUtil.setSqlStatement("INSERT INTO users VALUES (?,?,?,?,?,?,?,1);");
        dbUtil.setSqlParams(userName.getText(), passwordField.getText(), firstName.getText(),
                lastName.getText(), interest1.getText(), interest2.getText(), interest3.getText());
        try {
          resultSet =  dbUtil.processQuery(connection);
          if (!resultSet.next()) {
              GuiUtil.popupWindow(Alert.AlertType.ERROR, null, "Something went wrong. Please try again", "error");
          }else {
               GuiUtil.createWindow(event, "views/visitorUI.fxml", "visitor");
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fixPassword() {
        //post-condition: a pop up window appears saying password don't match. passwordField, passwordCheck are cleared
        GuiUtil.popupWindow(Alert.AlertType.ERROR, "Please re-enter your password", "passwords don't match!", "Error");
        passwordField.clear();
        passwordCheck.clear();

    }
}
