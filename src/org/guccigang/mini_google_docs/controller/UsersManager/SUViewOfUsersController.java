package org.guccigang.mini_google_docs.controller.UsersManager;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.guccigang.mini_google_docs.model.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class SUViewOfUsersController {
    private UserObject currentUser;
    private UserObject otherUser;

    @FXML
    private Label userNameLabel;
    @FXML
    private Label firstAndLastName;
    @FXML
    private Label listOfInterests;
    @FXML
    private Label usersDocumentLabel;
    @FXML
    private Button reportUserBTN;
    @FXML
    private Button inviteBTN;
    @FXML
    private Button upgradeMembershipBTN;
    @FXML
    private TableView<DocumentFile> documentFileTable;
    @FXML
    private TableColumn<DocumentFile, String> documentNameColumn;
    @FXML
    private TableColumn<DocumentFile, String> restrictionLevelColumn;

    SUViewOfUsersController (){
        this.currentUser = null;
        this.otherUser = null;
    }
    SUViewOfUsersController(UserObject currentUser, UserObject otherUser){
        this.currentUser = currentUser;
        this.otherUser = otherUser;
    }
    @FXML
    private void initialize(){
        userNameLabel.setText("User Name: " + otherUser.getUserName());
        firstAndLastName.setText("Full Name: " + otherUser.getFirstName() + " " + otherUser.getLastName());
        listOfInterests.setText(otherUser.getInterests().toString());
        usersDocumentLabel.setText(otherUser.getUserName() + "'s Documents");
        documentNameColumn.setCellValueFactory(cellData -> cellData.getValue().documentNameProperty());
        restrictionLevelColumn.setCellValueFactory(cellData -> cellData.getValue().restrictionLevelProperty());
        fillTable();
    }

    @FXML
    public void handleUpgradeMembership(ActionEvent event){
        if(otherUser.getMembershipLevel()>1){
            GuiUtil.createAlertWindow(Alert.AlertType.INFORMATION,"User is already at max level.","Upgrade Membership","Information");
        }else {
            UsersDAO.upgradeUserMembership(otherUser.getUserName());
            GuiUtil.createAlertWindow(Alert.AlertType.INFORMATION,"User has been upgraded","Upgrade Membership","Information");
        }
    }

    private void fillTable(){
        try{
            documentFileTable.setItems(DocumentDAO.getSpecificsUsersDocuments(otherUser.getUserName()));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void handleInviteToEdit() {
        try {
            ObservableList<DocumentFile>  allDocuments = DocumentDAO.getAllDocumentFilesDataForSuperUser();
            ArrayList<DocumentFile> documentList = new ArrayList<>(allDocuments);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
