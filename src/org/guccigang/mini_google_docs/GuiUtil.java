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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.guccigang.mini_google_docs.controller.VisitorTextEditorController;
import org.guccigang.mini_google_docs.model.DocumentFile;

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

    public static void createModalWindow(ActionEvent event, String designatedUI) throws IOException {
        //Precondition: designatedUI exists as an fxml file
        //Postcondition: A new Modal window is created. meaning a window that block access to other windows until closed.

        //create new window
        final Stage dialog = new Stage();

        //get access to the window calling this function
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        //define
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        Scene scene = new Scene(FXMLLoader.load(Main.class.getResource(designatedUI)));
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    public static void createWindow(ActionEvent event, String sourceUI, String title) throws IOException {
        //Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(sourceUI));

        //Create the new  stage.
        Stage visitorStage = new Stage();
        visitorStage.setTitle(title);
        Scene scene = new Scene(loader.load());
        visitorStage.setScene(scene);

        //Display the newly created window
        visitorStage.show();

        //destroy current window
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public static void changeScene(ActionEvent event, String sourceUI, String title) throws IOException {
        Node node = (Node) event.getSource();
        Scene window = (Scene) node.getScene();
        window.setRoot(FXMLLoader.load(Main.class.getResource(sourceUI)));
    }

    /*
    Opens Visitor Text Editor
    @param selectedDocument
    Takes in a DocumentFile object and sets the text editor to it.
     */
    public static void openVisitorTextReader(DocumentFile selectedDocumentFile){
        try{
            //Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/VisitorTextEditor.fxml"));
            AnchorPane textEditor = (AnchorPane) loader.load();

            //Create the view document stage.
            Stage textEditorStage = new Stage();
            textEditorStage.setTitle("Text Editor");
            Scene scene = new Scene(textEditor);
            textEditorStage.setScene(scene);
            textEditorStage.initModality(Modality.APPLICATION_MODAL);


            //Set the controller
            VisitorTextEditorController controller = loader.getController();
            //Takes the string and loads it in text editor.
            controller.setSelectedDocument(selectedDocumentFile);
            controller.setAreaText();
            textEditorStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
