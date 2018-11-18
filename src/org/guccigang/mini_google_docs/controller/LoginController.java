package org.guccigang.mini_google_docs.controller;

import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.guccigang.mini_google_docs.DbUtil;
import org.guccigang.mini_google_docs.GuiUtil;
import org.guccigang.mini_google_docs.Main;

public class LoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;

    //this variables will be used to change scene
    private Stage stage;
    private Scene scene;

    //these variables are used to query from database;
    private Connection connection;
    private DbUtil dbUtil;

    public LoginController() {
        try {
            dbUtil = new DbUtil();
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/document_system", "root", "password");//DBUtil.connectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loginAction (ActionEvent event) {
        //Event listener for pressing the login button
        //precondition: userNamefield.getText() != null && passwordField.getText() != null;
        //postcondition: user is sent to a profile screen
        int x = 0;
        dbUtil.setSqlParams(userNameField.getText(), passwordField.getText());
        dbUtil.setSqlStatement( "SELECT * FROM users WHERE userName= ? and password= ?");
        try {
            ResultSet resultSet = dbUtil.processQuery(connection);
            if(resultSet.next()) {
                if(resultSet.next()) {
                    GuiUtil.popupWindow(Alert.AlertType.CONFIRMATION, "Login Successful!", null, "Success");
                    x = resultSet.getInt("membershipLevel");

                    if (x == 1) {
                        //changeScene(event, "originalUserUI.fxml");
                    }

                    if (x == 2) {
                        //changeScene(event, "superUserUI.fxml");
                    }
                }
            } else {
                GuiUtil.popupWindow(Alert.AlertType.CONFIRMATION,
                        "Please enter correct username and password or login as visitor",
                        "Wrong username or password", "Failed");
            }

        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    public void signUpAction(ActionEvent event) {
        //post-condition: A new window opens on top of the sign in with sign up form.
        //Tis new window is MODAL meaning that it will block all other windows of the application until it is closed.
        try {
            GuiUtil.createModalWindow(event, "views/signUp.fxml");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void visitorAction(ActionEvent event) {
        try {
            GuiUtil.createWindow(event, "views/visitorUI.fxml", "Visitor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
