package org.guccigang.mini_google_docs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.guccigang.mini_google_docs.model.GuiUtil;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.UserObject;
import org.guccigang.mini_google_docs.model.UsersDAO;

import java.io.IOException;

public class SearchUsersWindowController {

    private UserObject currentUser;

    @FXML
    private TextField searchBar;
    @FXML
    private TableView<UserObject> usersTable;
    @FXML
    private TableColumn<UserObject, String> UserNameNameColumn;
    @FXML
    private TableColumn<UserObject, String> InterestColumn;
    @FXML
    private TableColumn<UserObject, String> MembershipColumn;

    SearchUsersWindowController(UserObject currentUser){
        this.currentUser = currentUser;
    }
    @FXML
    private void initialize(){
        fillTable();
        UserNameNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        InterestColumn.setCellValueFactory(cellData -> cellData.getValue().interestProperty(1));
        MembershipColumn.setCellValueFactory(cellData -> cellData.getValue().membershipProperty());

    }
    private void fillTable(){
        try{
            usersTable.setItems(UsersDAO.getAllUsers());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void handleDisplayAllUsers(ActionEvent event){
        fillTable();
    }
    @FXML
    public void handleSearch(ActionEvent event){
        usersTable.setItems(UsersDAO.getSearchedResult(searchBar.getText()));
    }
    @FXML
    public void handleOpenUserProfile(ActionEvent event){
        int selectedIndex = usersTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            UserObject otherUser = usersTable.getItems().get(selectedIndex);
            if(currentUser.getMembershipLevel() == 1){
                try{
                    OUViewOfUsersController controller = new OUViewOfUsersController(currentUser, otherUser);
                    GuiUtil.createWindow(event,"views/OUViewOfUsersWindow.fxml",otherUser.getUserName(),controller);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }else {

            }
        }else {
            //Nothing selected.
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Please select a user in the table.",
                    "No User Selected", "No Selection");
        }
    }
    @FXML
    public void handleHome(ActionEvent event){
        try{
            if(currentUser.getMembershipLevel()==1){
                OriginalUserUIController controller = new OriginalUserUIController(currentUser);
                GuiUtil.changeScene(event,"views/originalUserUI.fxml","Home", controller);
            }else {
                SuperUserUIController controller = new SuperUserUIController(currentUser);
                GuiUtil.changeScene(event,"views/superUserUI.fxml","Home", controller);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
