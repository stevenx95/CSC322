package org.guccigang.mini_google_docs.controller.DocumentControllers;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import org.guccigang.mini_google_docs.model.UILocation;
import org.guccigang.mini_google_docs.controller.UserUI.OriginalUserUIController;
import org.guccigang.mini_google_docs.controller.UserUI.SuperUserUIController;
import org.guccigang.mini_google_docs.model.*;

import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;


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
    @FXML
    private TextField searchAll;
    @FXML
    private TextField searchMy;

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
            try
            {
                SuperAndOriginalTextEditorController controller = new SuperAndOriginalTextEditorController(currentUser, selectedDocument);
                if(DocumentDAO.documentIsLocked(selectedDocument.getID()))
                {
                    if(selectedDocument.getOwner().equals(currentUser.getUserName()) || currentUser.getMembershipLevel() == 2)
                    {
                        if(GuiUtil.createConfirmationAlert(Alert.AlertType.CONFIRMATION,
                                "Would you like to unlock it?",
                                "Locked Document","Locked"))
                        {
                            DocumentDAO.unlockDocument(selectedDocument.getID());
                            handleOpenDocument();
                        }
                    }
                    else
                    {
                        GuiUtil.createAlertWindow(Alert.AlertType.ERROR, "Please try again later.",
                                "Locked Document", "Locked");
                    }
                }
                else {
                    GuiUtil.createWindow(UILocation.SUPER_AND_ORIGINAL_TEXT_EDITOR, "Text Editor", controller);
                    DocumentDAO.unlockDocument(selectedDocument.getID(), currentUser.getUserName());
                }
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

    public void handleCreateDocument(ActionEvent event)
    {
        String title = "untitled";
        int restriction;

        TextInputDialog dialog = new TextInputDialog("untitles");
        dialog.setTitle("Creating a Document");
        dialog.setHeaderText("Creating a Document");
        dialog.setContentText("Please name your document:");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) title = result.get();
        else
        {
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Document creation failed",
                    "Document Failed", "You did this. This is your fault");
            return;
        }
        try {
            VersionUtil.create(currentUser.getUserName(),title);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "You've just created a document. Check your documents",
                "Document Created", "New Document");
    }

    public void handleMyDocument(ActionEvent event)
    {
        fillTableMyDocs();
    }

    public void handlePublicDocument(ActionEvent event)
    {
        fillTable();
    }

    public void handleAllDocument(ActionEvent event)
    {
        if(currentUser.getMembershipLevel() == 2)
        {
            fillTableAll();
        }
        else
        {
            fillTable();
        }
    }

    public void handleSharedDocument(ActionEvent event)
    {
        fillTableShared();
    }
    @FXML
    private void handleSearchAllDocuments(ActionEvent event)
    {
        System.out.println(searchAll.getCharacters().toString());
    }
    @FXML
    private void handleSearchMyDocuments(ActionEvent event)
    {

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
        documentRestrictionColumn.setCellValueFactory(cellData -> cellData.getValue().restrictionLevelProperty());
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

    private void fillTableAll()
    {
        try{
            documentFileTable.setItems(DocumentDAO.getAllDocumentFilesDataForSuperUser());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
