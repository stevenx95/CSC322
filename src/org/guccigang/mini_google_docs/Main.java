package org.guccigang.mini_google_docs;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.guccigang.mini_google_docs.controller.TextEditorController;
import org.guccigang.mini_google_docs.controller.ViewDocumentsController;
import org.guccigang.mini_google_docs.model.DocumentFile;

import java.io.IOException;
import java.util.Date;

public class Main extends Application {
    //List of documents for table view in view document controller.
    private ObservableList<DocumentFile> documentFilesData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        //for testing purposes.
        showViewDocumentWindow();
    }

    /**
     * This function displays Jonathan's view document fxml file for testing purposes.
     */
    public static void showViewDocumentWindow(){
        try{
            //Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/ViewDocuments.fxml"));
            AnchorPane viewDocumentPage = (AnchorPane) loader.load();

            //Create the view document stage.
            Stage viewDocumentStage = new Stage();
            viewDocumentStage.setTitle("View Documents");
            Scene scene = new Scene(viewDocumentPage);
            viewDocumentStage.setScene(scene);

            //Set the controller
            ViewDocumentsController controller = loader.getController();
            //Gives a connection with main app and view documents controller
            //controller.setMainApp(this);
            viewDocumentStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void openTextEditor(String content){
        try{
            //Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/TextEditor.fxml"));
            AnchorPane textEditor = (AnchorPane) loader.load();

            //Create the view document stage.
            Stage textEditorStage = new Stage();
            textEditorStage.setTitle("Text Editor");
            Scene scene = new Scene(textEditor);
            textEditorStage.setScene(scene);

            //Set the controller
            TextEditorController controller = loader.getController();
            //Takes the string and loads it in text editor.
            controller.setAreaText(content);
            textEditorStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public Main(){

    }



    public static void main(String[] args) {
        launch(args);
    }

}
