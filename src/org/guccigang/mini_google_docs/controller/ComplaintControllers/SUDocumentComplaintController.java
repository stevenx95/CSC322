package org.guccigang.mini_google_docs.controller.ComplaintControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.guccigang.mini_google_docs.model.ComplaintDAO;
import org.guccigang.mini_google_docs.model.ComplaintText;
import org.guccigang.mini_google_docs.model.GuiUtil;
import org.guccigang.mini_google_docs.model.UserObject;

import java.sql.SQLException;

public class SUDocumentComplaintController {

    private UserObject currentUser;

    public SUDocumentComplaintController(UserObject currentUser){
        this.currentUser = currentUser;
    }

    public SUDocumentComplaintController() {
    }

    @FXML
    private Button resolveDocumentComplaint;

    @FXML
    private TableView<ComplaintText> documentComplaintsTable;

    @FXML
    private TableColumn<ComplaintText, String> docIDColumn;

    @FXML
    private TableColumn<ComplaintText, String> docVersionColumn;

    @FXML
    private TableColumn<ComplaintText, String> docOwnerColumn;

    @FXML
    private TableColumn<ComplaintText, String> docComplainerColumn;

    @FXML
    private TableColumn<ComplaintText, String> docComplaintColumn;

    @FXML
    private void initialize(){
        fillTable();

        docIDColumn.setCellValueFactory(cellData -> cellData.getValue().docComplaintDocIDProperty());
        docVersionColumn.setCellValueFactory(cellData -> cellData.getValue().docComplaintDocVersionProperty());
        docOwnerColumn.setCellValueFactory(cellData -> cellData.getValue().docComplaintDocOwnerProperty());
        docComplainerColumn.setCellValueFactory(cellData -> cellData.getValue().docComplaintDocComplainerProperty());
        docComplaintColumn.setCellValueFactory(cellData -> cellData.getValue().docComplainerMessageComplaintDocProperty());

    }

    public void fillTable(){
        try {
            documentComplaintsTable.setItems(ComplaintDAO.getAllComplaintTexts());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removeDocComplaintFromDB() throws  SQLException{

        ComplaintText complaintText = new ComplaintText();

        int selectedIndex = documentComplaintsTable.getSelectionModel().getSelectedIndex();

        if(selectedIndex >=0){
            complaintText = documentComplaintsTable.getItems().get(selectedIndex);
        }

        if (complaintText != null){
            ComplaintDAO.removeDocumentComplaint(complaintText.getDocID());
        }
        else {
            //Nothing selected.
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Please select a complaint from the table.",
                    "No Complaint Selected", "No Selection");
        }
    }


}
