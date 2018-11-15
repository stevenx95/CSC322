package org.guccigang.mini_google_docs.controller;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewDocumentsController {
    @FXML
    private Button homeButton;

    @FXML
    private GridPane documentLayout;

    private Stage viewDocumentStage;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ViewDocumentsController(){

    }
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){
        int columnIncrementor = 0;
        int rowIncrementor = 0;
        for(int i = 0; i < 200; i++){
            documentLayout.add(new VBox(new ImageView(),new Label("untitle.txt")),columnIncrementor,rowIncrementor);
            columnIncrementor++;
            if((i + 1) % 5 == 0){
                rowIncrementor ++;
                columnIncrementor = 0;
                documentLayout.addRow(1);
            }
        }



    }


}
