package org.guccigang.mini_google_docs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import org.guccigang.mini_google_docs.Main;
import org.guccigang.mini_google_docs.model.DocumentFile;

import java.io.File;
import java.util.Arrays;

public class TextEditorController {
    private Main mainApp;
    private DocumentFile selectedDocument;

    @FXML
    private TextArea areaText;


    @FXML
    private void onSave(){

    }
    @FXML
    private void onLoad(){

    }
    @FXML
    private void onClose(){
    }
    @FXML
    private void onAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("About");
        alert.setContentText("This is a simple text editor");
        alert.show();
    }
    @FXML
    private void initialize(String content){

    }
    public void setSelectedDocument(DocumentFile selectedDocument){
        this.selectedDocument = selectedDocument;
    }

    public void setAreaText(String docContent) {
        this.areaText.setText(docContent);
        this.areaText.setEditable(false);
    }

    /**
     * Is called by the main application to give a reference ack to itself
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
    }
}
