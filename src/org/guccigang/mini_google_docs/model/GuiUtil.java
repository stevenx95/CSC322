/*
    GuiUtil is an utilities class that contain methods that can be reused for different sections of our GUI
 */

package org.guccigang.mini_google_docs.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import org.guccigang.mini_google_docs.Main;
import org.guccigang.mini_google_docs.controller.VisitorTextEditorController;
import org.guccigang.mini_google_docs.model.DocumentFile;

import java.io.IOException;


public class GuiUtil {
    /**
     *
     * @param alertType
     *  An enum object from Alert class's AlertType enum array.
     * @param message
     *  Message to be display next to ok Button
     * @param header
     *  Text to be displayed on top of header and next to the AlertType logo
     * @param title
     *  title of newly create window.
     */

    public static void createAlertWindow(AlertType alertType, String message, String header, String title) {
        //postcondition: a new pop up window is shown with 'message' as body, 'header' as header and 'title' as title.
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.showAndWait();
    }

    /**
     *
     * @param event
     *  click event comming from user interaction wiht UI.
     * @param designatedUI
     *  this must be a an existing fxml file
     * @param title
     *  it will be display on top of the window
     * @throws IOException
     *   Postcondition: A new Modal window is created. meaning a window that block access to other windows until closed.
     */

    public static void createWindow(ActionEvent event, String designatedUI, String title) throws IOException {

        //create new window
        final Stage dialog = new Stage();

        //get access to the window calling this function
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        //define
        dialog.setTitle(title);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        Scene scene = new Scene(FXMLLoader.load(Main.class.getResource(designatedUI)));
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    /**
     *
     * @param event
     *  Action event comes from fxml file
     * @param sourceUI
     *  Must be a valid fxml file
     * @param title
     * @throws IOException
     *
     * This method creates a new window and destroys the previously opened window.
     *
     */

    public static void createWindowAndDestroy(ActionEvent event, String sourceUI, String title) throws IOException {
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

    public static <T> void createWindowAndDestroy(ActionEvent event, String sourceUI, String title, T controller) throws IOException {
        //define FXMLLoader and new Stage
        FXMLLoader loader = new FXMLLoader();
        Stage visitorStage = new Stage();

        //set controller
        loader.setController(controller);

        //create the new window
        loader.setLocation(Main.class.getResource(sourceUI));
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

    public static <T> void createWindow(ActionEvent event, String sourceUI, String title, T controller) throws IOException {
        //define FXMLLoader and new Stage
        FXMLLoader loader = new FXMLLoader();
        Stage visitorStage = new Stage();

        //set controller
        loader.setController(controller);

        //create the new window
        loader.setLocation(Main.class.getResource(sourceUI));
        visitorStage.setTitle(title);
        Scene scene = new Scene(loader.load());
        visitorStage.setScene(scene);

        //Display the newly created window
        visitorStage.show();
    }

    /**
     *
     * @param event
     * @param sourceUI
     * @param title
     * @throws IOException
     */

    public static void changeScene(ActionEvent event, String sourceUI, String title) throws IOException {
        Node node = (Node) event.getSource();
        Scene scene = node.getScene();
        Stage window = (Stage) node.getScene().getWindow();
        window.setTitle(title);
        scene.setRoot(FXMLLoader.load(Main.class.getResource(sourceUI)));
    }

    public static <T> void changeScene(ActionEvent event, String sourceUI, String title, T controller)  throws IOException {
        //define our variables
        Node node = (Node) event.getSource();
        Scene scene = node.getScene();
        Stage window = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(sourceUI));

        //set controller
        loader.setController(controller);

        //change the scene
        scene.setRoot(loader.load());
        window.setTitle(title);
    }

    /**
    Opens Visitor Text Editor
    @param selectedDocumentFile
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

    /**
     *
     * @param event
     *  An action event that comes from click a button
     *  post-condition: The window from which the event is called is closed
     */

    public static void closeWindow(ActionEvent event) {
        Node node = (Node) event.getSource();
        //this variable are used to manipulate the IU.
        Stage window = (Stage) node.getScene().getWindow();
        window.close();
    }

}
