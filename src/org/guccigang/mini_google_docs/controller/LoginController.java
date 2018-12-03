package org.guccigang.mini_google_docs.controller;

import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.guccigang.mini_google_docs.model.DbUtil;
import org.guccigang.mini_google_docs.model.GuiUtil;
import org.guccigang.mini_google_docs.model.UserObject;

public class LoginController {

    private UserObject currentUser;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;


    public void loginAction (ActionEvent event) {
        //Event listener for pressing the login button
        //precondition: userNamefield.getText() != null && passwordField.getText() != null;
        //postcondition: user is sent to a profile screen
        String sql = "SELECT * FROM users WHERE userName= ? and password= ?";
        try {
            ResultSet resultSet = DbUtil.processQuery(sql, userNameField.getText(), passwordField.getText());
            if(resultSet.next()) {
                GuiUtil.createAlertWindow(Alert.AlertType.CONFIRMATION, "Login Successful!", null, "Success");
                loadUserProfile(event, resultSet);
            } else {
                GuiUtil.createAlertWindow(Alert.AlertType.CONFIRMATION,
                        "Please enter correct username and password or login as visitor",
                        "Wrong username or password", "Failed");
            }

        } catch (Exception e ) {
            e.printStackTrace();
            visitorAction(event);
        }
    }

    private void loadUserProfile(ActionEvent event, ResultSet resultSet) throws SQLException, IOException {
        this.currentUser = new UserObject(resultSet.getString("username"), resultSet.getString("password"),
                resultSet.getString("firstname"), resultSet.getString("lastname"),
                resultSet.getInt("membershiplevel"));

        if (currentUser.getMembershipLevel() == 1) {
            OriginalUserUIController controller = new OriginalUserUIController(currentUser);
            GuiUtil.createWindowAndDestroy(event, "views/originalUserUI.fxml", currentUser.getFirstName(), controller);
        }

        if (currentUser.getMembershipLevel() == 2) {
            SuperUserUIController controller = new SuperUserUIController(currentUser);
            GuiUtil.createWindowAndDestroy(event, "views/superUserUI.fxml", currentUser.getFirstName(), controller);
        }
    }

    public void signUpAction(ActionEvent event) {
        //post-condition: A new window opens on top of the sign in with sign up form.
        //Tis new window is MODAL meaning that it will block all other windows of the application until it is closed.
        try {
            GuiUtil.createWindow(event, "views/signUp.fxml", "Sign Up");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void visitorAction(ActionEvent event) {
        try {
            GuiUtil.createWindowAndDestroy(event, "views/visitorUI.fxml", "Visitor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
