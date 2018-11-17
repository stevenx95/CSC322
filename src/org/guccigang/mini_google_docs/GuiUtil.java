/*
    GuiUtil is an utilities class that contain methods that can be reused for different sections of our GUI
 */

package org.guccigang.mini_google_docs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class GuiUtil {
    public static void popupWindow(AlertType alertType, String message, String header, String title) {
        //postcondition: a new pop up window is shown with 'message' as body, 'header' as header and 'title' as title.
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
