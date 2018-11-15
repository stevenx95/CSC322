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
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;

    @FXML
    private Button login;

    @FXML
    private Button signup;

    @FXML
    private Button guestAccess;

    //this variables will be used to change scene
    private Stage stage;
    private Scene scene;

    //these variables are used to query from database;
    private Connection connection;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public LoginController() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/document_system", "root", "password");//DbUtil.connectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loginAction (ActionEvent event) {
        //Event listener for pressing the login button
        //precondition: userNamefield.getText() != null && passwordField.getText() != null;
        //postcondition: user is sent to a profile screen
        String userName = userNameField.getText();
        String password = passwordField.getText();
        String sql = "SELECT * FROM users WHERE userName= ? and password= ?";
        try {
            resultSet = processQuery(sql, userName, password);
            if(!resultSet.next()) {
                popupWindow("Please enter correct username and password", "Wrong username or password", "Failed");
            } else {
                popupWindow("Login Successful!", null, "Success");
                changeScene(event, "visitor.fxml");

            }
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    private ResultSet processQuery(String sql, String param1, String param2) throws SQLException {
        //precondition: sql is a valid SQL statement  with ? for param1, param2;
        //param1 and param2 two can be inserted into sql for execution.
        //postcondition: a resultset object is returned containing the results from the query as a set or it is null.
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, param1);
        preparedStatement.setString(2, param2);
        return preparedStatement.executeQuery();
    }

    private void changeScene(ActionEvent event, String dest) throws IOException {
        //precondition: event should be a transition event such as hitting submit button. Link must be an existing fxml file.
        //postcondition: The schene of current Stage is change to that of dest fxml file.
        Node node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        stage.close();
        scene = new Scene(FXMLLoader.load(getClass().getResource(dest)));
        stage.setScene(scene);
        stage.show();
    }

    public static void popupWindow(String message, String header, String title) {
        //postcondition: a new pop up window is shown with 'message' as body, 'header' as header and 'title' as title.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.showAndWait();
    }

}
