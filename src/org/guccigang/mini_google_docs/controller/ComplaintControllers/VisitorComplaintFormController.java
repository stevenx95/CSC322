package org.guccigang.mini_google_docs.controller.ComplaintControllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.guccigang.mini_google_docs.model.*;

import java.sql.*;
import java.util.Calendar;

public class VisitorComplaintFormController {

    @FXML
    TextField textBarUsername;

    @FXML
    TextArea complaintText;

    private DocumentFile docFile;

    public VisitorComplaintFormController(){
        this(null);
    }

    public VisitorComplaintFormController(DocumentFile docFile) {
        this.docFile = docFile;
    }

    public void submitComplaintToDB(ActionEvent event) throws SQLException {
        int tempDocId = docFile.getID();
        int result = 0;

        try {
            String sql = "INSERT INTO complaints(DocId,version,owner,complainer,message) VALUES ('" + tempDocId + "','" + getVersionOfDocument(tempDocId) + "', '" + docFile.getOwner() + "', '" + textBarUsername.getText() + "','" + complaintText.getText() + "')";

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/322project", "root", "Starpoint29");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            result = preparedStatement.executeUpdate(sql);


            if (result == 0) {
                GuiUtil.createAlertWindow(Alert.AlertType.ERROR, null, "Something went wrong. Please try again", "error");
            } else {
                GuiUtil.createAlertWindow(Alert.AlertType.CONFIRMATION, "Complaint has been sent!",
                        "Complaint submitted successfully!,", "Confirmation");
                GuiUtil.closeWindow(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getVersionOfDocument(Integer docID) throws SQLException {
       int version = 0;

        String sqlStatement = "Select docID FROM revisions WHERE docID = ? ";
        ResultSet rs =DbUtil.processQuery(sqlStatement,statement -> statement.setInt(1,docID));
        if(rs.next()){
             version = rs.getInt("version");
        }

        return version;
    }

    private boolean isTaken(String userName) {
        ResultSet resultSet = DbUtil.processQuery("SELECT * FROM users WHERE username = ?", statement-> statement.setString(1, userName));
        try {
            if(!resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
