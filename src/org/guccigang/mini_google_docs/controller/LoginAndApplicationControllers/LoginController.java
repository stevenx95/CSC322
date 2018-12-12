package org.guccigang.mini_google_docs.controller.LoginAndApplicationControllers;

import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.guccigang.mini_google_docs.controller.UserUI.VisitorUIController;
import org.guccigang.mini_google_docs.model.UILocation;

import org.guccigang.mini_google_docs.controller.TabooControllers.TabooDocumentReviewController;
import org.guccigang.mini_google_docs.controller.UserUI.OriginalUserUIController;
import org.guccigang.mini_google_docs.controller.UserUI.SuperUserUIController;
import org.guccigang.mini_google_docs.model.DbUtil;
import org.guccigang.mini_google_docs.model.GuiUtil;
import org.guccigang.mini_google_docs.model.TabooUtil;
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
            ResultSet resultSet = DbUtil.processQuery(sql, statement-> {
                statement.setString(1,userNameField.getText());
                statement.setString(2,passwordField.getText());
            });
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
            GuiUtil.createWindowAndDestroy(event, UILocation.ORIGINAL_USER_UI, currentUser.getFirstName(), controller);
            while(TabooUtil.isUserFlaged(currentUser.getUserName())){
                GuiUtil.createAlertWindow(Alert.AlertType.WARNING,"Remove all Taboo Words AND 'UNK'. Until this is done, This window will not go away until you review and alter all flagged documents","Document Review","Warning");
                TabooDocumentReviewController TabooController = new TabooDocumentReviewController(currentUser);
                GuiUtil.createWindow(UILocation.TABOO_DOCUMENT_REVIEW_UI, "Review Documents",TabooController);
            }
        }

        if (currentUser.getMembershipLevel() == 2) {
            SuperUserUIController controller = new SuperUserUIController(currentUser);
            GuiUtil.createWindowAndDestroy(event, UILocation.SUPER_USER_UI, currentUser.getFirstName(), controller);
            while(TabooUtil.isUserFlaged(currentUser.getUserName())){
                GuiUtil.createAlertWindow(Alert.AlertType.WARNING,"Remove all Taboo Words AND 'UNK'. Until this is done, This window will not go away until you review and alter all flagged documents","Document Review","Warning");
                TabooDocumentReviewController TabooController = new TabooDocumentReviewController(currentUser);
                GuiUtil.createWindow(UILocation.TABOO_DOCUMENT_REVIEW_UI, "Review Documents",TabooController);
            }
        }

    }

    public void signUpAction(ActionEvent event) {
        //post-condition: A new window opens on top of the sign in with sign up form.
        //Tis new window is MODAL meaning that it will block all other windows of the application until it is closed.
        try {
            GuiUtil.createWindow(event, UILocation.SIGNUP, "Sign Up");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void visitorAction(ActionEvent event) {
        try {
            VisitorUIController controller = new VisitorUIController();
            GuiUtil.createWindowAndDestroy(event, UILocation.VISITOR_UI, "Visitor",controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
