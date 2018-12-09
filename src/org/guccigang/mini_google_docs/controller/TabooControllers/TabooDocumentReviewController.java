package org.guccigang.mini_google_docs.controller.TabooControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import org.guccigang.mini_google_docs.UILocation;
import org.guccigang.mini_google_docs.model.*;

import java.io.IOException;

public class TabooDocumentReviewController {
    private UserObject currentUser;

    @FXML
    private TableView<DocumentFile> documentFileTable;
    @FXML
    private TableColumn<DocumentFile, String> documentNameColumn;
    @FXML
    private TableColumn<DocumentFile, String> documentOwnerColumn;

    TabooDocumentReviewController(){
        this.currentUser = null;
    }

    public TabooDocumentReviewController(UserObject currentUser){
        this.currentUser = currentUser;
    }

    public void handleOpenDocument(ActionEvent event)throws IOException{
        int selectedIndex = documentFileTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            DocumentFile selectedDocument = documentFileTable.getItems().get(selectedIndex);
            TabooDocumentReviewEditorController controller = new TabooDocumentReviewEditorController(currentUser, selectedDocument);
            GuiUtil.createWindow(event, UILocation.TABOO_DOCUMENT_REVIEW_EDITOR,"Document Editor", controller);
        }else {
            //Nothing selected.
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Please select a document in the table.",
                    "No Document Selected", "No Selection");
        }
    }

    @FXML
    private void initialize(){
        //Files the TableView with documents.
        fillTable();

        if(documentFileTable.getItems().isEmpty()){
            String SQLStatement = "UPDATE users set DocumentTabooReviewFlag = 0 where userName = ?";
            DbUtil.executeUpdateDB(SQLStatement, currentUser.getUserName());

        }
        // Initialize the person table with the two columns.
        documentNameColumn.setCellValueFactory(cellData -> cellData.getValue().documentNameProperty());
        documentOwnerColumn.setCellValueFactory(cellData -> cellData.getValue().ownerProperty());

        /**Listens for selection changes and when the user clicks open document on while
         *while document is highlighted then that exact document should open.
         */
        // documentFileTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->{} ));
    }

    private void fillTable(){
        try{
            documentFileTable.setItems(DocumentDAO.getTabooFlagedDocuments(currentUser.getUserName()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
