package org.guccigang.mini_google_docs.controller;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.guccigang.mini_google_docs.GuiUtil;
import org.guccigang.mini_google_docs.model.DocumentDAO;
import org.guccigang.mini_google_docs.model.DocumentFile;

import javafx.fxml.FXML;



public class SuperAndOriginalDocManager {
    @FXML
    private TableView<DocumentFile> documentFileTable;
    @FXML
    private TableColumn<DocumentFile, String> documentNameColumn;
    @FXML
    private TableColumn<DocumentFile, String> documentOwnerColumn;
    @FXML
    private TableColumn<DocumentFile, String> documentRestrictionColumn;

    /**
     * Constructor
     */
    SuperAndOriginalDocManager(){

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){

    }
    private void fillTable(){
        documentNameColumn.setCellValueFactory(cellData -> cellData.getValue().documentNameProperty());
        documentOwnerColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        documentRestrictionColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());

    }
}
