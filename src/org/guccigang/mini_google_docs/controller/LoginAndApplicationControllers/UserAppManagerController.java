package org.guccigang.mini_google_docs.controller.LoginAndApplicationControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import org.guccigang.mini_google_docs.UILocation;
import org.guccigang.mini_google_docs.controller.UserUI.SuperUserUIController;
import org.guccigang.mini_google_docs.model.GuiUtil;
import org.guccigang.mini_google_docs.model.UsersDAO;
import org.guccigang.mini_google_docs.model.UserObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class UserAppManagerController implements Initializable {

    private UserObject currentUser;
    @FXML
    private TableView<UserObject> applicationsTable;

    @FXML
    private TableColumn<UserObject, String> userName;

    @FXML
    private TableColumn<UserObject, String> password;

    @FXML
    private TableColumn<UserObject, String> firstName;
    @FXML

    private TableColumn<UserObject, String> lastName;
    @FXML

    private TableColumn<UserObject, String> interest1;
    @FXML

    private TableColumn<UserObject, String> interest2;

    @FXML
    private TableColumn<UserObject, String> interest3;


    public UserAppManagerController() {
        this(null);
    }

    public UserAppManagerController(UserObject currentUser) {
        this.currentUser = currentUser;
    }


    @FXML
    public void handleHome(ActionEvent event) {
        try {
            SuperUserUIController controller = new SuperUserUIController(currentUser);
            GuiUtil.changeScene(event, UILocation.SUPER_USER_UI, currentUser.getFirstName(), controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        applicationsTable.setItems(UsersDAO.getAllApplications());
        userName.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        password.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        firstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        interest1.setCellValueFactory(cellData -> cellData.getValue().interestProperty(0));
        interest2.setCellValueFactory(cellData -> cellData.getValue().interestProperty(1));
        interest3.setCellValueFactory(cellData -> cellData.getValue().interestProperty(2));
    }
}
