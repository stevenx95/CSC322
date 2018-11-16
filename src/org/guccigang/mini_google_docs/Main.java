package org.guccigang.mini_google_docs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.guccigang.mini_google_docs.controller.ViewDocumentsController;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        //for testing purposes.
       // showViewDocumentWindow();
    }

    public void showViewDocumentWindow(){
        try{
            //Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/ViewDocuments.fxml"));
            GridPane viewDocumentPage = (GridPane) loader.load();

            //Create the view document stage.
            Stage viewDocumentStage = new Stage();
            viewDocumentStage.setTitle("View Documents");
            Scene scene = new Scene(viewDocumentPage);
            viewDocumentStage.setScene(scene);

            //Set the controller
            ViewDocumentsController controller = loader.getController();
            viewDocumentStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
