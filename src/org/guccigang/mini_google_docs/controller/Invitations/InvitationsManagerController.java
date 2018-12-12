package org.guccigang.mini_google_docs.controller.Invitations;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.event.ActionEvent;
import org.guccigang.mini_google_docs.controller.DocumentControllers.SuperAndOriginalDocManagerController;
import org.guccigang.mini_google_docs.model.*;


import java.net.URL;
import java.util.ResourceBundle;

public class InvitationsManagerController implements Initializable {

    private UserObject currentUser;

    @FXML
    private TableView<InvitationObject> invitationsTable;

    @FXML
    private TableColumn<InvitationObject, String> docNameColumn;

    @FXML
    private TableColumn<InvitationObject, String> ownerColumn;

    @FXML
    private TableColumn<InvitationObject, String> dateColumn;


    public InvitationsManagerController() {
        this(null);
    }

    public InvitationsManagerController(UserObject currentUser) {
        this.currentUser = currentUser;
    }

    public void handleHome(ActionEvent event) {
        SuperAndOriginalDocManagerController.ReturnToProfile(event,currentUser);
    }

    public void handleInvitationAccept() {
        int selectedIndex = invitationsTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            InvitationObject invitation = invitationsTable.getItems().get(selectedIndex);
            SharingUtil.shareDoc(invitation);
            GuiUtil.createAlertWindow(Alert.AlertType.CONFIRMATION, "You can now edit this document", "Invitation accepted!", "Confirmation");
            invitationsTable.getItems().remove(selectedIndex);
        } else {
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Please select an invitation", "no invitation selected", "Waring");
        }
    }

    public void handleInvitationDecline() {
        int selectedIndex = invitationsTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            InvitationObject invitation = invitationsTable.getItems().get(selectedIndex);
            SharingUtil.removeInvitation(invitation);
            GuiUtil.createAlertWindow(Alert.AlertType.CONFIRMATION,
                    "This item has been removed from you invitation list",
                    "Invitation Rejected",
                    "Confirmation");
            invitationsTable.getItems().remove(selectedIndex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(currentUser.getMembershipLevel() == 2) {
            invitationsTable.setItems(SharingUtil.getAllInvitationsForSuperUser());
        } else {
            invitationsTable.setItems(SharingUtil.getAllInvitations(currentUser));
        }
        docNameColumn.setCellValueFactory(cellData -> cellData.getValue().getDocNameProperty());
        ownerColumn.setCellValueFactory(cellData -> cellData.getValue().getOwnerProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
    }
}
