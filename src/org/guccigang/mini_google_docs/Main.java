package org.guccigang.mini_google_docs;

import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.DebugUtil;

public class Main extends Application {
    //List of documents for table view in view document controller.
    private ObservableList<DocumentFile> documentFilesData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/LoginAndApplicationUI/login.fxml"));
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Mini Google Docs Login");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Debug escape character
        scene.setOnKeyPressed(e ->
        {
            Scanner keyboard = new Scanner(System.in);
            if (e.getCode() == KeyCode.BACK_QUOTE) {
                System.out.println("Enter debug command:");
                DebugUtil.execCommand(keyboard.nextLine().split(" "));
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }

}
