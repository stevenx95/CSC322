package org.guccigang.mini_google_docs.model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SharingUtil {

    public static void shareDoc(InvitationObject invitation) {
        String query1 = "INSERT INTO sharedDocs VALUE (?,?)",
                query2 = "UPDATE documents SET restricted= ? WHERE docId=?";
        DbUtil.executeUpdateDB(query1, pstmt -> {
            pstmt.setString(1,invitation.getUsername());
            pstmt.setInt(2,invitation.getDocId());
        });
        DbUtil.executeUpdateDB(query2, pstmt-> {
            pstmt.setInt(1,1);
            pstmt.setInt(2,invitation.getDocId());
        });
        removeInvitation(invitation);
    }

    public static int unshareDoc(DocumentFile selectedDoc, String currentUser, String selectedUser) {
        int deletions = 0;
        if(selectedDoc.getOwner().equals(currentUser)) {
            deletions = DbUtil.executeUpdateDB(
                    "DELETE FROM sharedDocs WHERE username=? AND docdID = ?",
                    pstmt -> {
                        pstmt.setString(1, selectedUser);
                        pstmt.setInt(2, selectedDoc.getID());
                    }
            );
        }
        return deletions;
    }

    public static boolean isShared(int docId) {
        ResultSet ressultset = DbUtil.processQuery(
                "SELECT * FROM documents WHERE docId=?",
                pstmt-> pstmt.setInt(1, docId)
                );
        try {
            if(ressultset.next() && ressultset.getInt("membershiplevel") == DocRestriction.SHARED.id) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void processInvitation(DocumentFile selectedDocument, String selectedUser) {
        String query = "INSERT INTO invitations VALUE(?,?,?,?,CURRENT_DATE)";
        DbUtil.executeUpdateDB(query, pstmt -> {
            pstmt.setInt(1, selectedDocument.getID());
            pstmt.setString(2, selectedDocument.getOwner());
            pstmt.setString(3,selectedUser);
            pstmt.setString(4, selectedDocument.getDocumentName());
        });
    }

    public static void removeInvitation(InvitationObject invitation) {
        String query = "DELETE FROM invitations WHERE docId=?";
        DbUtil.executeUpdateDB(query, pstmt ->pstmt.setInt(1,invitation.getDocId()));
    }

    public static ObservableList<InvitationObject> getAllInvitations(UserObject currentUser){
        String sqlStatement = "SELECT * FROM  invitations WHERE username=?";
        ObservableList<InvitationObject> invitations = null;
        try {
            ResultSet resultSet = DbUtil.processQuery(sqlStatement, pstmt -> pstmt.setString(1,currentUser.getUserName()));
            invitations = createInvitationList(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invitations;
    }

    public static ArrayList<String> getAllUsers(String currentUser) {
        ArrayList<String> userNames = new ArrayList<>();
        try {
            ObservableList<UserObject> userObjects = UsersDAO.getAllUsers(currentUser);
            System.out.println(userObjects.toString());
            userObjects.forEach(user -> userNames.add(user.getUserName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userNames;
    }
    public static ArrayList<String> getSharingUsers(String currentUser){
        ArrayList<String> userList = new ArrayList<>();
        ResultSet resultSet = DbUtil.processQuery(
                "SELECT * FROM documents WHERE owner=? AND restrictionlevel=?",
                pstmt -> {
                    pstmt.setString(1, currentUser);
                    pstmt.setInt(2, DocRestriction.SHARED.id);
                }
        );
        try {
            while (resultSet.next()) {
                userList.add(resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();;
        }
        return userList;
    }

    public static ObservableList<InvitationObject> getAllInvitationsForSuperUser(){
        String sqlStatement = "SELECT * FROM  invitations";
        ObservableList<InvitationObject> invitations = null;
        try {
            ResultSet resultSet = DbUtil.processQuery(sqlStatement);
            invitations = createInvitationList(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invitations;
    }

    private static ObservableList<InvitationObject> createInvitationList(ResultSet resultSet) throws SQLException {
        ObservableList<InvitationObject> invitationsList = FXCollections.observableArrayList();
        while(resultSet.next()) {
            InvitationObject invitationObject = new InvitationObject(
                    resultSet.getInt("docId"),
                    resultSet.getString("owner"),
                    resultSet.getString("userName"),
                    resultSet.getString("docName"),
                    resultSet.getDate("createdDate").toString()
            );
            invitationsList.add(invitationObject);
        }
        return invitationsList;
    }
}
