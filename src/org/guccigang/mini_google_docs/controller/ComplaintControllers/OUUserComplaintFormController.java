package org.guccigang.mini_google_docs.controller.ComplaintControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.guccigang.mini_google_docs.model.DbUtil;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.GuiUtil;
import org.guccigang.mini_google_docs.model.UserObject;

import java.sql.*;

public class OUUserComplaintFormController {

    @FXML
    TextArea complaintText;

    private DocumentFile docFile;

    private UserObject currentUser;



    public OUUserComplaintFormController(DocumentFile docFile, UserObject currentUser){
        this.docFile = docFile;
        this.currentUser = currentUser;
    }

    public  OUUserComplaintFormController(){
        this.docFile = null;
        this.currentUser = null;
    }

    public void submitUserComplaintToDB(ActionEvent event) throws SQLException {

        int tempDocId = docFile.getID();
        int result = 0;

        String userNameInput = "";

        if(currentUser == null){
            userNameInput = "visitor";
        }
        else {
            userNameInput = currentUser.getUserName();
        }

         try {
            String sql = "INSERT INTO complaintsuser(owner,version,complainer,violator,message,DocID) VALUES ('" + docFile.getOwner() + "','" + getVersionOfDocument(tempDocId) + "', '" + userNameInput + "', '" + getViolatorOfDocument(tempDocId) + "','" + complaintText.getText() + "','" + tempDocId+ "')";

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
    public void submitComplaint(ActionEvent actionEvent){
        if(currentUser == null){
            String SQLstatement = "INSERT INTO complaints (DocID, owner, complainer, message)values(?,?,?,?)";
            DbUtil.executeUpdateDB(SQLstatement,String.valueOf(docFile.getiD()),docFile.getOwner(),"Visitor",complaintText.getText());
        }else {
            String SQLstatement = "INSERT INTO complaints (DocID, owner, complainer, message)values(?,?,?,?)";
            DbUtil.executeUpdateDB(SQLstatement,String.valueOf(docFile.getiD()),docFile.getOwner(),currentUser.getUserName(),complaintText.getText());
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
