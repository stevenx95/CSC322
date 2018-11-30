package org.guccigang.mini_google_docs;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.guccigang.mini_google_docs.model.DocumentFile;

public class Main extends Application {
    //List of documents for table view in view document controller.
    private ObservableList<DocumentFile> documentFilesData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));
        primaryStage.setTitle("Mini Google Docs Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }

}
