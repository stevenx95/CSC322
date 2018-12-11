package org.guccigang.mini_google_docs.controller.UsersManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import org.guccigang.mini_google_docs.UILocation;
import org.guccigang.mini_google_docs.controller.DocumentControllers.SuperAndOriginalDocManagerController;
import org.guccigang.mini_google_docs.model.GuiUtil;
import org.guccigang.mini_google_docs.model.UserObject;
import org.guccigang.mini_google_docs.model.UsersDAO;

import java.io.IOException;

public class SUOUUsersManagerController {

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

    public SUOUUsersManagerController(){
        this.currentUser = null;
    }

    public SUOUUsersManagerController(UserObject currentUser){
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
            usersTable.setItems(UsersDAO.getAllUsers(currentUser.getUserName()));
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
        usersTable.setItems(UsersDAO.getSearchedResult(searchBar.getText(), currentUser.getUserName()));
    }
    @FXML
    public void handleOpenUserProfile(){
        int selectedIndex = usersTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            UserObject otherUser = usersTable.getItems().get(selectedIndex);
            if(currentUser.getMembershipLevel() == 1){
                try{
                    OUViewOfUsersController controller = new OUViewOfUsersController(currentUser, otherUser);
                    GuiUtil.createWindow(UILocation.OU_VIEW_OF_USERS_WINDOW,otherUser.getUserName(),controller);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }else {
                try{
                    SUViewOfUsersController controller = new SUViewOfUsersController(currentUser, otherUser);
                    GuiUtil.createWindow(UILocation.SU_VIEW_OF_USERS_WINDOW,otherUser.getUserName(),controller);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else {
            //Nothing selected.
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Please select a user in the table.",
                    "No User Selected", "No Selection");
        }
    }
    @FXML
    public void handleHome(ActionEvent event){
        SuperAndOriginalDocManagerController.ReturnToProfile(event, currentUser);
    }


}
