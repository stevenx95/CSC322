package org.guccigang.mini_google_docs.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComplaintDAO {

    public static ObservableList<ComplaintText> getAllComplaintTexts() throws SQLException {
        String selectStatement = "SELECT * FROM complaints";

        try{
            ResultSet rs = DbUtil.processQuery(selectStatement);
            ObservableList<ComplaintText> complaintTextsData = getAllComplaintsForDocumentsList(rs);
            return complaintTextsData;
        }catch (SQLException e){
            System.out.println("SQL query has failed " + e);
            throw e;
        }
    }


    private static ObservableList<ComplaintText> getAllComplaintsForDocumentsList(ResultSet resultSet)throws SQLException{

        ObservableList<ComplaintText> complaintTexts= FXCollections.observableArrayList();

        while(resultSet.next()){
            ComplaintText complaintText = new ComplaintText();
            complaintText.setDocID(resultSet.getInt("docID"));
            complaintText.setVersion(resultSet.getInt("version"));
            complaintText.setOwner(resultSet.getString("owner"));
            complaintText.setComplainer(resultSet.getString("complainer"));
            complaintText.setMessage(resultSet.getString("message"));
            complaintTexts.add(complaintText);
        }
        return complaintTexts;
    }

    public static void removeDocumentComplaint(Integer ID) throws SQLException{
        String selectStatement = "DELETE FROM complaints WHERE DocID = ?";
        DbUtil.executeUpdateDB(selectStatement,ID.toString());
    }
    
    
}
