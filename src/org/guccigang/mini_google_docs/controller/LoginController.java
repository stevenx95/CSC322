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
        dbUtil.setSqlParams(userNameField.getText(), passwordField.getText());
        dbUtil.setSqlStatement( "SELECT * FROM users WHERE userName= ? and password= ?");
        try {
            ResultSet resultSet = dbUtil.processQuery(connection);
            if(!resultSet.next()) {
                GuiUtil.popupWindow(Alert.AlertType.CONFIRMATION,"Please enter correct username and password", "Wrong username or password", "Failed");
            } else {
                GuiUtil.popupWindow(Alert.AlertType.CONFIRMATION,"Login Successful!", null, "Success");
                changeScene(event, "visitorUI.fxml");

            }
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    public void signUpAction(ActionEvent event) {
        //post-condition: A new window opens on top of the sign in with sign up form.
        //Tis new window is MODAL meaning that it will block all other windows of the application until it is closed.
        try {
            final Stage dialog = new Stage();
            Node node = (Node) event.getSource();
            stage = (Stage) node.getScene().getWindow();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(stage);
            scene = new Scene(FXMLLoader.load(Main.class.getResource("views/signUp.fxml")));
            dialog.setScene(scene);
            dialog.showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void visitorAction(ActionEvent event) {
        try {
            //Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/visitorUI.fxml"));

            //Create the view document stage.
            Stage visitorStage = new Stage();
            visitorStage.setTitle("Guest Profile");
            Scene scene = new Scene(loader.load());
            visitorStage.setScene(scene);

            //Set the controller
            ControllerVisitorUI controller = loader.getController();
            //Gives a connection with main app and view documents controller
            visitorStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeScene(ActionEvent event, String dest) throws IOException {
        //precondition: event should be a transition event such as hitting submit button. Link must be an existing fxml file.
        //postcondition: The schene of current Stage is change to that of dest fxml file.
        Node node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        stage.close();
        scene = new Scene(FXMLLoader.load(Main.class.getResource(dest)));
        stage.setScene(scene);
        stage.show();
    }
}
