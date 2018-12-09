package org.guccigang.mini_google_docs.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentDAO {

    /**
     * Queries the database for readable documents for visitor ONLY.
     * @return documentFiles ObservableList
     * @throws SQLException
     */
    public static ObservableList<DocumentFile> getAllDocumentFilesDataForVisitor() throws SQLException{
        String selectStatement = "SELECT * FROM documents where restricted >= 2";
        //Execute select statement
        try{

            ResultSet resultSet = DbUtil.processQuery(selectStatement);
            ObservableList<DocumentFile> documentFiles = getDocumentsList(resultSet);
            return documentFiles;

        }catch (SQLException e){
            System.out.println("SQL query has failed" + e);
            throw e;
        }
    }

    /**
     * Queries the database for all documents owned by userName.
     * @Params userName
     * @return documentFiles ObservableList
     * @throws SQLException
     */
    public static ObservableList<DocumentFile> getSpecificsUsersDocuments(String userName) throws SQLException{
        String selectStatement = "SELECT * FROM documents where owner = ?";
        //Execute select statement
        try{
            ResultSet resultSet = DbUtil.processQuery(selectStatement,userName);
            ObservableList<DocumentFile> documentFiles = getDocumentsList(resultSet);
            return documentFiles;

        }catch (SQLException e){
            System.out.println("SQL query has failed" + e);
            throw e;
        }
    }
    public static ObservableList<DocumentFile> getDocumentsForTabooReview(String userName) throws SQLException{
        String selectStatement = "SELECT * FROM documents where owner = ?";
        //Execute select statement
        try{
            ResultSet resultSet = DbUtil.processQuery(selectStatement,userName);
            ObservableList<DocumentFile> documentFiles = getDocumentsForTabooReviewList(resultSet);
            return documentFiles;

        }catch (SQLException e){
            System.out.println("SQL query has failed" + e);
            throw e;
        }
    }

    /**
     * Returns documents containing taboo words
     * @param resultSet
     * @return documentFiles ObservableList
     * @throws SQLException
     */
    private static ObservableList<DocumentFile> getDocumentsForTabooReviewList(ResultSet resultSet)throws SQLException{
        ObservableList<DocumentFile> documentFiles = FXCollections.observableArrayList();
        while(resultSet.next()){
            if(TabooUtil.containsTaboo(resultSet.getString("content"))){
                DocumentFile document = new DocumentFile();
                document.setiD(resultSet.getInt("docID"));
                document.setOwner(resultSet.getString("owner").toString());
                document.setDocumentName(resultSet.getString("docName"));
                document.setContent(resultSet.getString("content"));
                document.setLock(resultSet.getInt("isLocked"));
                document.setRestricted(resultSet.getInt("restricted"));
                //document.setDate(new Date(resultSet.getString("createdDate")));
                document.setTabooFlag(resultSet.getInt("tabooFlag"));
                documentFiles.add(document);
            }
        }
        return documentFiles;
    }

    /**
     * @param resultSet
     * @return documentFiles ObservableList
     * @throws SQLException
     */
    private static ObservableList<DocumentFile> getDocumentsList(ResultSet resultSet)throws SQLException{
        ObservableList<DocumentFile> documentFiles = FXCollections.observableArrayList();
        while(resultSet.next()){
            DocumentFile document = new DocumentFile();
            document.setiD(resultSet.getInt("docID"));
            document.setOwner(resultSet.getString("owner").toString());
            document.setDocumentName(resultSet.getString("docName"));
            document.setContent(resultSet.getString("content"));
            document.setLock(resultSet.getInt("isLocked"));
            document.setRestricted(resultSet.getInt("restricted"));
            //document.setDate(new Date(resultSet.getString("createdDate")));
            document.setTabooFlag(resultSet.getInt("tabooFlag"));
            documentFiles.add(document);
        }
        return documentFiles;
    }
}
