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
            ObservableList<DocumentFile> documentFiles = getAllDocumentFilesDataList(resultSet);
            return documentFiles;
        }catch (SQLException e){
            System.out.println("SQL query has failed" + e);
            throw e;
        }
    }

    public static ObservableList<DocumentFile> getTabooFlagedDocuments(String owner) throws SQLException{
        String selectStatement = "SELECT * FROM documents where owner = ? AND tabooFlag = 1";
        //Execute select statement
        try{
            ResultSet resultSet = DbUtil.processQuery(selectStatement,owner);
            ObservableList<DocumentFile> documentFiles = getAllDocumentFilesDataList(resultSet);
            return documentFiles;
        }catch (SQLException e){
            System.out.println("SQL query has failed" + e);
            throw e;
        }
    }

    public static ObservableList<DocumentFile> getAllDocumentFilesDataForSuperUser() throws SQLException{
        String selectStatement = "SELECT * FROM documents";

        try{
            ResultSet resultSet = DbUtil.processQuery(selectStatement);
            ObservableList<DocumentFile> documentFiles = getAllDocumentFilesDataList(resultSet);
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
            ObservableList<DocumentFile> documentFiles = getAllDocumentFilesDataList(resultSet);
            return documentFiles;

        }catch (SQLException e){
            System.out.println("SQL query has failed" + e);
            throw e;
        }
    }
    public static ObservableList<DocumentFile> getDocumentsForTabooReview(String userName) throws SQLException{
        String selectStatement = "SELECT * FROM documents where owner = ? AND tabooFlag = 1";
        //Execute select statement
        try{
            ResultSet resultSet = DbUtil.processQuery(selectStatement,userName);
            ObservableList<DocumentFile> documentFiles = getAllDocumentFilesDataList(resultSet);
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
     * @throws ClassNotFoundException
     */

    public static ObservableList<DocumentFile> getSharedUsersDocuments(String userName) throws SQLException
    {
        String selectStatement = "select * from documents natural join sharedDocs where userName = ?;";
        try{
            ResultSet resultSet = DbUtil.processQuery(selectStatement,userName);
            ObservableList<DocumentFile> documentFiles = getAllDocumentFilesDataList(resultSet);
            return documentFiles;

        }catch (SQLException e){
            System.out.println("SQL query has failed" + e);
            throw e;
        }
    }

    /**
     * @param resultSet
     * @return documentFiles ObservableList
     * @throws SQLException
     */
    private static ObservableList<DocumentFile> getAllDocumentFilesDataList(ResultSet resultSet)throws SQLException{
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

    public static boolean documentIsLockedBy(int docID,String userName) {
        String sqlStatement = "SELECT * FROM locks WHERE docID = " + docID+ " AND userName = \""+ userName+"\"";
        ResultSet resultSet = DbUtil.processQuery(sqlStatement);
        try {
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean documentIsLocked(int docID)
    {
        String sqlStatement = "SELECT * FROM locks WHERE docID = " + docID;
        ResultSet resultSet = DbUtil.processQuery(sqlStatement);
        try {
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static void lockDocument(int docID,String userName)
    {
        String sqlStatement = "INSERT INTO locks (docID, userName) values (?,?)";
        DbUtil.executeUpdateDB(sqlStatement,""+docID,userName);
    }

    public static void unlockDocument(int docID,String userName)
    {
        String sqlStatement = "DELETE FROM locks where docID=? AND userName=?";
        if(documentIsLockedBy(docID,userName))
            DbUtil.executeUpdateDB(sqlStatement,""+docID,userName);
    }

    public static boolean isShared(DocumentFile doc, String userName)
    {
        String selectStatement = "select * from documents natural join sharedDocs where userName = ? and docID = ?;";
        try{
            ResultSet resultSet = DbUtil.processQuery(selectStatement,userName, ""+doc.getID());
            return resultSet.next();

        }catch (SQLException e){
            System.out.println("SQL query has failed" + e);
        }
        return false;
    }

    public static boolean canWrite(DocumentFile doc, String userName)
    {
        String sqlStatement = "SELECT * FROM documents WHERE docID = " + doc.getID();
        ResultSet resultSet = DbUtil.processQuery(sqlStatement);
        try {
            resultSet.next();
            int restricted = resultSet.getInt("restricted");
            if(documentIsLocked(doc.getID()) && !documentIsLockedBy(doc.getID(),userName))
            {
                return false;
            }
            if (restricted == 3) {
                return true;
            }
            System.out.println("The doc is not restricted");
            if (isShared(doc,userName))
            {
                return true;
            }
            System.out.println("The doc is not shared");
            if (doc.getOwner().equals(userName))
            {
                return true;
            }
            System.out.println("The doc is not owned");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
