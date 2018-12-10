package org.guccigang.mini_google_docs.controller.DocumentControllers;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.guccigang.mini_google_docs.UILocation;
import org.guccigang.mini_google_docs.controller.UserUI.OriginalUserUIController;
import org.guccigang.mini_google_docs.controller.UserUI.SuperUserUIController;
import org.guccigang.mini_google_docs.model.GuiUtil;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.DocumentDAO;

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
        ReturnToProfile(event, currentUser);
    }

    public static void ReturnToProfile(ActionEvent event, UserObject currentUser) {
        try{
            if(currentUser.getMembershipLevel()==1){
                OriginalUserUIController controller = new OriginalUserUIController(currentUser);
                GuiUtil.changeScene(event, UILocation.ORIGINAL_USER_UI,"Home", controller);
            }else {
                SuperUserUIController controller = new SuperUserUIController(currentUser);
                GuiUtil.changeScene(event,UILocation.SUPER_USER_UI,"Home", controller);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleOpenDocument()
    {

        int selectedIndex = documentFileTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            DocumentFile selectedDocument = documentFileTable.getItems().get(selectedIndex);
            if(DocumentDAO.documentIsLocked(selectedDocument.getID()))
                GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Document is locked! Wait for it to unlock",
                        "Locked Document", "Locked Document");
            try
            {
                DocumentDAO.lockDocument(selectedDocument.getID());
                SuperAndOriginalTextEditorController controller = new SuperAndOriginalTextEditorController(currentUser, selectedDocument);
                GuiUtil.createWindow(UILocation.SUPER_AND_ORIGINAL_TEXT_EDITOR,"Text Editor", controller);
            }catch (java.io.IOException e)
            {
                e.printStackTrace();
                GuiUtil.createAlertWindow(Alert.AlertType.ERROR, "Please try again later.", "An error occurred.", "Error");

            }

        }else {
            //Nothing selected.
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Please select a document in the table.",
                    "No Document Selected", "No Selection");
        }
    }

    public void handleMyDocument(ActionEvent event)
    {
        fillTableMyDocs();
    }

    public void handlePublicDocument(ActionEvent event)
    {
        fillTable();
    }

    public void handleSharedDocument(ActionEvent event)
    {
        System.out.println("Yes");
        fillTableShared();
    }


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {
        documentNameColumn.setCellValueFactory(cellData -> cellData.getValue().documentNameProperty());
        documentOwnerColumn.setCellValueFactory(cellData -> cellData.getValue().ownerProperty());
        documentRestrictionColumn.setCellValueFactory(cellData -> cellData.getValue().restrictedProperty());
        fillTableMyDocs();
    }

    private void fillTableMyDocs()
    {
        try{
            documentFileTable.setItems(DocumentDAO.getSpecificsUsersDocuments(currentUser.getUserName()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void fillTableShared()
    {
        try{
            documentFileTable.setItems(DocumentDAO.getSharedUsersDocuments(currentUser.getUserName()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void fillTable()
    {
        try{
            documentFileTable.setItems(DocumentDAO.getAllDocumentFilesDataForVisitor());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
