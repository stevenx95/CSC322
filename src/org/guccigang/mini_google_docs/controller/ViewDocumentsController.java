package org.guccigang.mini_google_docs.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.guccigang.mini_google_docs.model.DocumentDAO;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.Main;

import java.sql.SQLException;

public class ViewDocumentsController {
    @FXML
    private Button homeButton;
    @FXML
    private Button openDocButton;
    @FXML
    private TableView<DocumentFile> documentFileTable;
    @FXML
    private TableColumn<DocumentFile, String> documentNameColumn;
    @FXML
    private TableColumn<DocumentFile, String> documentOwnerColumn;

    //Reference to the main application.
    private Stage viewDocumentStage;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ViewDocumentsController(){

    }

    /**
     * This function is called when the user clicks open document.
     */
    @FXML
    public void handleOpenDocument(){
        int selectedIndex = documentFileTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            Main.openTextEditor(documentFileTable.getItems().get(selectedIndex).getContent());
        }else {
            //Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Document Selected");
            alert.setContentText("Please select a document in the table.");
            alert.showAndWait();
        }
    }
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){
        //fillTable();
        // Initialize the person table with the two columns.
        documentNameColumn.setCellValueFactory(cellData -> cellData.getValue().documentNameProperty());
        documentOwnerColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());

        /**Listens for selection changes and when the user clicks open document on while
         *while document is highlighted then that exact document should open.
         */
        documentFileTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->{} ));
    }

    /**
     * This function acesses the data base and fills the table with all the documents of the data base.
     */
//    public void fillTable(){
//        try{
//            documentFileTable.setItems(DocumentDAO.getAllDocumentFilesData());
//        }catch (SQLException e){
//            e.printStackTrace();
//        }catch (ClassNotFoundException e){
//            e.printStackTrace();
//        }
//    }

//    /**
//     * Is called by the main application to give a reference ack to itself
//     *
//     * @param mainApp
//     */
//    public void setMainApp(Main mainApp){
//        this.mainApp = mainApp;
//    }





}
