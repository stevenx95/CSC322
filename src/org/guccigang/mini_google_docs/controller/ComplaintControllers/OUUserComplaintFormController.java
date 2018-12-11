package org.guccigang.mini_google_docs.controller.ComplaintControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.guccigang.mini_google_docs.model.DbUtil;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.GuiUtil;

import java.sql.*;

public class OUUserComplaintFormController {

    @FXML
    TextField textBarUsername;

    @FXML
    TextArea complaintText;

    private DocumentFile docFile;

    public OUUserComplaintFormController(DocumentFile docFile){
        this.docFile = docFile;
    }

    public  OUUserComplaintFormController(){
        this(null);
    }

    public void submitUserComplaintToDB(ActionEvent event) throws SQLException {

        int tempDocId = docFile.getID();
        int result = 0;

         try {
            String sql = "INSERT INTO complaintsuser(owner,version,complainer,violator,message,DocID) VALUES ('" + docFile.getOwner() + "','" + getVersionOfDocument(tempDocId) + "', '" + textBarUsername.getText() + "', '" + getViolatorOfDocument(tempDocId) + "','" + complaintText.getText() + "','" + tempDocId+ "')";

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GucciGangDB", "root", "password");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            result = preparedStatement.executeUpdate(sql);

            if (result == 0) {
                GuiUtil.createAlertWindow(Alert.AlertType.ERROR, null, "Something went wrong. Please try again", "error");
            } else {
                GuiUtil.createAlertWindow(Alert.AlertType.CONFIRMATION, "User Complaint has been sent!",
                        "User Complaint submitted successfully!,", "Confirmation");
                GuiUtil.closeWindow(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private int getVersionOfDocument(Integer docID) throws SQLException {
        int version = 0;

        String sqlStatement = "Select version FROM revisions WHERE docID = ? ";
        ResultSet rs = DbUtil.processQuery(sqlStatement,docID.toString());
        if(rs.next()){
            version = rs.getInt("version");
        }

        return version;
    }

    private String getViolatorOfDocument(Integer docID) throws  SQLException{
        String violator="";

        String sqlStatement = "Select author FROM revisions WHERE docID = ? ";
        ResultSet rs = DbUtil.processQuery(sqlStatement,docID.toString());
        if(rs.next()){
            violator = rs.getString("author");
        }
        return violator;

    }

}
