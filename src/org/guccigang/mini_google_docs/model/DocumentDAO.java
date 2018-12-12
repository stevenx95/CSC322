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
        return getDocumentFiles(selectStatement);
    }
    /**
     * Queries the database for readable documents for visitor ONLY.
     * @return documentFiles ObservableList
     * @throws SQLException
     */
    public static ObservableList<DocumentFile> getTopViewDocumentForVisitor() throws SQLException{
        String selectStatement = "SELECT * FROM documents where restricted >= 2 ORDER BY views DESC";
        //Execute select statement
        return getDocumentFiles(selectStatement);
    }

    private static ObservableList<DocumentFile> getDocumentFiles(String selectStatement) throws SQLException {
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
        return getDocumentFiles(owner, selectStatement);
    }

    private static ObservableList<DocumentFile> getDocumentFiles(String owner, String selectStatement) throws SQLException {
        try{
            ResultSet resultSet = DbUtil.processQuery(selectStatement, statement -> statement.setString(1,owner));
            ObservableList<DocumentFile> documentFiles = getAllDocumentFilesDataList(resultSet);
            return documentFiles;
        }catch (SQLException e){
            System.out.println("SQL query has failed" + e);
            throw e;
        }
    }

    public static ObservableList<DocumentFile> getAllDocumentFilesDataForSuperUser() throws SQLException{
        String selectStatement = "SELECT * FROM documents";

        return getDocumentFiles(selectStatement);
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
            ResultSet resultSet = DbUtil.processQuery(selectStatement,statement -> statement.setString(1,userName));
            ObservableList<DocumentFile> documentFiles = getAllDocumentFilesDataList(resultSet);
            return documentFiles;

        }catch (SQLException e){
            System.out.println("SQL query has failed" + e);
            throw e;
        }
    }

    public static ObservableList<DocumentFile> searchSpecificsUsersDocuments(String userName, String term) throws SQLException
    {
        String selectStatement = "SELECT * FROM documents where owner = \""+userName+"\" AND content LIKE '%"+term+"%';";
        // SELECT * FROM documents where owner = "Jon" AND content LIKE '%Three%';
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

    public static ObservableList<DocumentFile> getSpecificsUsersReadableDocuments(String userName) throws SQLException{
        String selectStatement = "SELECT * FROM documents where owner = ? AND restricted >= 2";
        //Execute select statement
        try{
            ResultSet resultSet = DbUtil.processQuery(selectStatement,statement -> statement.setString(1,userName));
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
        return getDocumentFiles(userName, selectStatement);
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
        String selectStatement = "select * from documents natural join sharedDocs where userName = ? AND restricted = 1;";
        try{
            ResultSet resultSet = DbUtil.processQuery(selectStatement,statement -> statement.setString(1,userName));
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
            document.setRestricted(DocRestriction.getDocRestriction(resultSet.getInt("restricted")));
            //document.setDate(new Date(resultSet.getString("createdDate")));
            document.setTabooFlag(resultSet.getInt("tabooFlag"));
            document.setCounter(resultSet.getInt("views"));
            documentFiles.add(document);
        }
        return documentFiles;
    }

    public static boolean documentIsLockedBy(int docID,String userName) {
        //System.out.println("Your username is: "+userName);
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
    public static void unlockDocument(int docID)
    {
        String sqlStatement = "DELETE FROM locks where docID=?";
        if(documentIsLocked(docID))
            DbUtil.executeUpdateDB(sqlStatement,""+docID);
    }

    public static boolean isShared(DocumentFile doc, String userName)
    {
        String selectStatement = "select * from documents natural join sharedDocs where userName = ? and docID = ?;";
        try{
            ResultSet resultSet = DbUtil.processQuery(selectStatement, statement-> {
                statement.setString(1,userName);
                statement.setInt(2, doc.getID());
            });
            return resultSet.next();

        }catch (SQLException e){
            System.out.println("SQL query has failed" + e);
        }
        return false;
    }

    public static boolean canWrite(DocumentFile doc, UserObject user)
    {


        String userName = user.getUserName();
        String sqlStatement = "SELECT * FROM documents WHERE docID = " + doc.getID();
        ResultSet resultSet = DbUtil.processQuery(sqlStatement);
        try {
            resultSet.next();
            int restricted = resultSet.getInt("restricted");
            if(documentIsLocked(doc.getID()))
            {
                System.out.println("The document is locked");
                if(documentIsLockedBy(doc.getID(),userName))
                {
                    System.out.println("You locked it!");
                    return true;
                }
                return false;
            }
            if(user.getMembershipLevel() ==2) return true;
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
