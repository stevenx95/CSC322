package org.guccigang.mini_google_docs.controller.ComplaintControllers;

import javafx.fxml.FXML;
import org.guccigang.mini_google_docs.model.ComplaintText;
import org.guccigang.mini_google_docs.model.DbUtil;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.UserObject;

import javax.sql.rowset.CachedRowSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitorComplaintFormController {

    @FXML
    private TextField textBarUsername;

    @FXML
    private TextField complaintText;

    @FXML
    private Button complaintSubmitButton;

    private DocumentFile docFile;

    public VisitorComplaintFormController(){}

    public VisitorComplaintFormController(DocumentFile docFile) {
        this.docFile = docFile;
    }


    public void submitComplaintToDB(ActionEvent event){
        ComplaintText complainTextObject = new ComplaintText();

        complainTextObject.setComplainer(textBarUsername.getText());
        complainTextObject.setMessage(complaintText.getText());
     // complainTextObject.setVersion(getVersionOfDocument());





    }

    private int getVersionOfDocument(Integer docID) throws SQLException {
       int version = 0;

        String sqlStatement = "Select docID FROM revisions WHERE docID = ? ";
        ResultSet rs =DbUtil.processQuery(sqlStatement,docID.toString());
        if(rs.next()){
             version = rs.getInt("version");
        }

        return version;
    }




    private boolean isTaken(String userName) {
        ResultSet resultSet = DbUtil.processQuery("SELECT * FROM users WHERE username = ?", userName);
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
