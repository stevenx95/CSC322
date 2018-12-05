package org.guccigang.mini_google_docs.controller.UsersManager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.guccigang.mini_google_docs.model.DbUtil;
import org.guccigang.mini_google_docs.model.DocumentDAO;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.UserObject;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This controller is for when we open another user's profile.
 */
public class OUViewOfUsersController {
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
    private TableView<DocumentFile> documentFileTable;
    @FXML
    private TableColumn<DocumentFile, String> documentNameColumn;
    @FXML
    private TableColumn<DocumentFile, String> restrictionLevelColumn;

    OUViewOfUsersController (){
        this.currentUser = null;
        this.otherUser = null;
    }
    OUViewOfUsersController(UserObject currentUser, UserObject otherUser){
        this.currentUser = currentUser;
        this.otherUser = otherUser;
    }
    @FXML
    private void initialize(){
        userNameLabel.setText("User Name: " + otherUser.getUserName());
        firstAndLastName.setText("Full Name: " + otherUser.getFirstName() + " " + otherUser.getLastName());
        listOfInterests.setText(otherUser.getInterests().toString());
        usersDocumentLabel.setText(otherUser.getUserName() + "' Documents");
        documentNameColumn.setCellValueFactory(cellData -> cellData.getValue().documentNameProperty());
        restrictionLevelColumn.setCellValueFactory(cellData -> cellData.getValue().restrictionLevelProperty());
        fillTable();
    }
    private void fillTable(){
        try{
            documentFileTable.setItems(DocumentDAO.getSpecificsUsersDocuments(otherUser.getUserName()));
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }


}
