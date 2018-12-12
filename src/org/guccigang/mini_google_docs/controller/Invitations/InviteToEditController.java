package org.guccigang.mini_google_docs.controller.Invitations;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.guccigang.mini_google_docs.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InviteToEditController  implements Initializable {

    private UserObject inviterUser;
    private UserObject inviteeUser;

    @FXML
    private TableView<DocumentFile> documentsTable;

    @FXML
    private TableColumn<DocumentFile, String> docName;

    @FXML
    private TableColumn<DocumentFile, String> owner;

    @FXML
    private TableColumn<DocumentFile, String> restriction;

    public InviteToEditController() {
        this(null,null);
    }

    public InviteToEditController(UserObject inviterUser, UserObject inviteeUser) {
        this.inviterUser = inviterUser;
        this.inviteeUser = inviteeUser;
    }
    
    public void handleSendInvitation() {
        int selectionIndex = documentsTable.getSelectionModel().getSelectedIndex();
        if(selectionIndex >= 0) {
            DocumentFile selectedDoc = documentsTable.getItems().get(selectionIndex);
                SharingUtil.processInvitation(selectedDoc,inviteeUser.getUserName());
        } else {
            GuiUtil.createAlertWindow(Alert.AlertType.ERROR, "please make a selection in the applications table",
                    "No user selected", "Error");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if(inviterUser.getMembershipLevel() == 2) {
                documentsTable.setItems(DocumentDAO.getAllDocumentFilesDataForSuperUser());
            } else {
                documentsTable.setItems(DocumentDAO.getSpecificsUsersDocuments(inviterUser.getUserName()));
            }
            docName.setCellValueFactory(cellData -> cellData.getValue().documentNameProperty());
            owner.setCellValueFactory(cellData -> cellData.getValue().ownerProperty());
            restriction.setCellValueFactory(cellData -> cellData.getValue().restrictionLevelProperty());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
