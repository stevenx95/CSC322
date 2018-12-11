package org.guccigang.mini_google_docs.controller.ComplaintControllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.guccigang.mini_google_docs.UILocation;
import org.guccigang.mini_google_docs.controller.UserUI.OriginalUserUIController;
import org.guccigang.mini_google_docs.controller.UserUI.SuperUserUIController;
import org.guccigang.mini_google_docs.model.*;
import org.guccigang.mini_google_docs.model.UserComplaint;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OUUserComplaintViewController {

    private UserObject currentUser;

    public OUUserComplaintViewController(UserObject currentUser){
        this.currentUser = currentUser;
    }
    
    public OUUserComplaintViewController(){}

    @FXML
    private Button resolveDocumentComplaint;

    @FXML
    private TableView<UserComplaint> documentComplaintsTable;

    @FXML
    private TableColumn<UserComplaint, String> docIDColumn;

    @FXML
    private TableColumn<UserComplaint, String> docVersionColumn;

    @FXML
    private TableColumn<UserComplaint, String> docOwnerColumn;

    @FXML
    private TableColumn<UserComplaint, String> docComplainerColumn;

    @FXML
    private TableColumn<UserComplaint, String> docComplaintColumn;
    
    @FXML
    private TableColumn<UserComplaint,String> docViolatorColumn;

    @FXML
    private void initialize(){
        fillTable();

        docOwnerColumn.setCellValueFactory(cellData -> cellData.getValue().ownerProperty());
        docVersionColumn.setCellValueFactory(cellData -> cellData.getValue().docComplaintDocVersionProperty());
        docComplainerColumn.setCellValueFactory(cellData -> cellData.getValue().complainerProperty());
        docViolatorColumn.setCellValueFactory(cellData -> cellData.getValue().violatorProperty());
        docComplaintColumn.setCellValueFactory(cellData -> cellData.getValue().messageProperty());
        docIDColumn.setCellValueFactory(cellData -> cellData.getValue().docIDProperty());

    }

    public void fillTable(){
        try {
            documentComplaintsTable.setItems(ComplaintDAO.getAllUserComplaintTexts(currentUser));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void removeDocComplaintFromDB(){
        return;
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
    
}
