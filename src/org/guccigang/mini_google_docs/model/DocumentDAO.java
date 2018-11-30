package org.guccigang.mini_google_docs.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.guccigang.mini_google_docs.DbUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentDAO {


    public static ObservableList<DocumentFile> getAllDocumentFilesData() throws SQLException, ClassNotFoundException{
        String selectStatement = "SELECT * FROM documents";
        //Execute select statment
        try{

            ResultSet resultSet = DbUtil.processQuery(selectStatement);
            ObservableList<DocumentFile> documentFilesData = getDocumentList(resultSet);
            return documentFilesData;

        }catch (SQLException e){
            System.out.println("SQL query has failed" + e);
            throw e;
        }
    }

    //Use ResultSet from DB as parameter and set Document attributes as such then returns every document from database.
    public static ObservableList<DocumentFile> getDocumentList(ResultSet resultSet)throws SQLException{
        ObservableList<DocumentFile> documentsList = FXCollections.observableArrayList();

        while(resultSet.next()){
            DocumentFile document = new DocumentFile();

            document.setiD(resultSet.getInt("docID"));
            document.setUserName(resultSet.getString("userName").toString());
            document.setDocumentName(resultSet.getString("docName"));
            document.setContent(resultSet.getString("content"));
            document.setLock(resultSet.getInt("isLocked"));
            document.setRestricted(resultSet.getInt("restricted"));
            //document.setDate(new Date(resultSet.getString("createdDate")));
            document.setTabooFlag(resultSet.getInt("tabooFlag"));

            documentsList.add(document);
        }
        return documentsList;
    }
}
