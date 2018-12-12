package org.guccigang.mini_google_docs.controller.UserUI;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.guccigang.mini_google_docs.controller.DocumentControllers.VisitorTextEditorController;
import org.guccigang.mini_google_docs.controller.DocumentControllers.VisitorViewDocumentsController;
import org.guccigang.mini_google_docs.controller.TabooControllers.OriginalAndVisitorReportTabooWordController;
import org.guccigang.mini_google_docs.model.DocumentDAO;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.UILocation;

import org.guccigang.mini_google_docs.model.GuiUtil;

import java.io.IOException;

public class VisitorUIController {
    @FXML
    private TableView<DocumentFile> documentFileTable;
    @FXML
    private TableColumn<DocumentFile, String> documentNameColumn;
    @FXML
    private TableColumn<DocumentFile, String> documentOwnerColumn;
    @FXML
    private TableColumn<DocumentFile, String> documentViewCountColumn;

    public VisitorUIController(){

    }
    @FXML
    private void initialize(){
        //Files the TableView with documents.
        fillTable();
        // Initialize the person table with the two columns.
        documentNameColumn.setCellValueFactory(cellData -> cellData.getValue().documentNameProperty());
        documentOwnerColumn.setCellValueFactory(cellData -> cellData.getValue().ownerProperty());
        documentViewCountColumn.setCellValueFactory(cellData -> cellData.getValue().counterProperty());

        /**Listens for selection changes and when the user clicks open document on while
         *while document is highlighted then that exact document should open.
         */
        // documentFileTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->{} ));
    }

    public void signUpAction(ActionEvent event) {
        //post-condition: A new window opens on top of the sign in with sign up form.
        //Tis new window is MODAL meaning that it will block all other windows of the application until it is closed.
        try {
            GuiUtil.createWindow(event, UILocation.SIGNUP, "Sign Up");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signOutButton(ActionEvent event){
        try {

            GuiUtil.createWindowAndDestroy(event,UILocation.LOGIN, "Gucci Gang");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleViewDocumentManager(ActionEvent event){
        try{
            VisitorViewDocumentsController controller = new VisitorViewDocumentsController();
            GuiUtil.changeScene(event, UILocation.VISITOR_VIEW_DOCUMENTS,"View Documents",controller);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * This function is called when the user clicks open document.
     */
    @FXML
    private void handleOpenDocument(){
        int selectedIndex = documentFileTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            DocumentFile selectedDocument = documentFileTable.getItems().get(selectedIndex);
            try {
                VisitorTextEditorController controller = new VisitorTextEditorController(selectedDocument);
                GuiUtil.createWindow(UILocation.VISITOR_TEXT_EDITOR, "Text Editor", controller);
            } catch (Exception e) {
                e.printStackTrace();
                GuiUtil.createAlertWindow(Alert.AlertType.ERROR, "Please try again later.", "An error occurred.", "Error");
            }
        }else {
            //Nothing selected.
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Please select a document in the table.",
                    "No Document Selected", "No Selection");
        }
    }

    public void reportTabooWordAction(ActionEvent event){
            try {
                OriginalAndVisitorReportTabooWordController controller = new OriginalAndVisitorReportTabooWordController();
                GuiUtil.createWindow(UILocation.ORIGINAL_AND_VISITOR_REPORT_TABOO_WORD, "Report Taboo Word",controller);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void fillTable(){
        try{

             documentFileTable.setItems(DocumentDAO.getAllDocumentFilesDataForVisitor());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
