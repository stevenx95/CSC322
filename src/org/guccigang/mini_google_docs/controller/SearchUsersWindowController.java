package org.guccigang.mini_google_docs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.guccigang.mini_google_docs.GuiUtil;
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
        InterestColumn.setCellValueFactory(cellData -> cellData.getValue().interestsProperty());
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
    public void handleSearch(ActionEvent event){
        usersTable.setItems(UsersDAO.getSearchedResult(searchBar.getText()));
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
