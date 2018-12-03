package org.guccigang.mini_google_docs.controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.guccigang.mini_google_docs.GuiUtil;
import org.guccigang.mini_google_docs.model.DocumentFile;

import javafx.fxml.FXML;
import org.guccigang.mini_google_docs.model.UserObject;

import java.io.IOException;


public class SuperAndOriginalDocManagerController {
    private UserObject currentUser;
    @FXML
    private TableView<DocumentFile> documentFileTable;
    @FXML
    private TableColumn<DocumentFile, String> documentNameColumn;
    @FXML
    private TableColumn<DocumentFile, String> documentOwnerColumn;
    @FXML
    private TableColumn<DocumentFile, String> documentRestrictionColumn;
    @FXML
    private Button homeButton;

    public SuperAndOriginalDocManagerController(UserObject currentUser) {
        this.currentUser = currentUser;
    }

    public SuperAndOriginalDocManagerController() {
        this(null);
    }
    @FXML
    public void handleHome(ActionEvent event){
        try{
            OriginalUserUIController controller = new OriginalUserUIController(currentUser);
            GuiUtil.changeScene(event,"views/originalUserUI.fxml","Home", controller);
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){
        documentNameColumn.setCellValueFactory(cellData -> cellData.getValue().documentNameProperty());
        documentOwnerColumn.setCellValueFactory(cellData -> cellData.getValue().ownerProperty());
        documentRestrictionColumn.setCellValueFactory(cellData -> cellData.getValue().restrictedProperty());
    }

}
